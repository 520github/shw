package com.babeeta.appstore.manager.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.dao.BrandDao;
import com.babeeta.appstore.dao.DeviceDao;
import com.babeeta.appstore.dao.SubscriptionRelationDao;
import com.babeeta.appstore.entity.Brand;
import com.babeeta.appstore.entity.Device;
import com.babeeta.appstore.entity.SubscriptionRelation;
import com.babeeta.appstore.manager.notification.Message;
import com.babeeta.appstore.manager.notification.MessagePush;
import com.babeeta.appstore.manager.service.BrandService;

/**
 * 品牌业务接口
 * 
 * @author chongf
 * 
 */
@Service("brandService")
public class BrandServiceImpl implements BrandService {
	private static final Logger logger = LoggerFactory
			.getLogger(BrandServiceImpl.class);

	private static String toPinyin(String resource) {
		HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
		outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		outputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		StringBuilder buf = new StringBuilder();
		for (char ch : resource.toCharArray()) {
			try {
				String[] result = PinyinHelper.toHanyuPinyinStringArray(ch,
						outputFormat);
				if (result == null) {
					buf.append(ch);
				} else {
					buf.append(result[0]);
				}
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				logger.warn(e.toString());
			}
		}
		return buf.toString().toLowerCase();
	}

	private BrandDao brandDao;
	private SubscriptionRelationDao subscriptionRelationDao;

	private MessagePush messagePush;

	private DeviceDao deviceDao;

	private boolean compare(Map<String, Date> map,
			List<SubscriptionRelation> list) {
		for (SubscriptionRelation obj : list) {
			if (obj.getLastVisitDate() == null
					|| obj.getLastVisitDate().before(map.get(obj.getBrandId()))) {
				return true;
			}
		}
		return false;
	}

	private Map<String, Date> enabledBrand() {
		List<Brand> list = brandDao.findEnabledBrands();
		Map<String, Date> map = new HashMap<String, Date>();
		for (Brand temp : list) {
			if (temp.getLastModified() != null) {
				map.put(temp.getId(), temp.getLastModified());
			}
		}
		return map;
	}

	@Override
	public List<Brand> findAll() {
		return brandDao.findAll();
	}

	@Override
	public Brand findById(String id) {
		return brandDao.findById(id);
	}

	@Override
	public Brand findByName(String name) {
		return brandDao.findByName(name);
	}

	@Override
	public void notice() {
		List<String> tokens = subscriptionRelationDao.distinctToken();
		Map<String, Date> map = enabledBrand();
		HttpClient httpClient = messagePush.getHttpConnection();
		for (String token : tokens) {
			List<SubscriptionRelation> list = subscriptionRelationDao
					.getSubscriptionRelationListByToken(token);
			if (compare(map, list)) {
				Device device = deviceDao.findDeviceByToken(token);
				if (device != null && device.getClientId() != null
						&& messagePush.pushMessage(httpClient,
								device.getClientId(),
								Message.getBrandUpdateMessage()) != null) {
					logger.debug("push message:" + token);
				}
			}
		}
		httpClient.getConnectionManager().shutdown();
	}

	@Override
	public void remove(String id) {
		brandDao.remove(id);
	}

	@Override
	public Brand save(Brand brand) {
		if (brand.getAsciiName() == null) {
			brand.setAsciiName(toPinyin(brand.getName()));
		}
		if (brand.getLastModified() == null) {
			brand.setLastModified(new Date());
		}
		return brandDao.save(brand);
	}

	@Autowired
	public void setBrandDao(BrandDao brandDao) {
		this.brandDao = brandDao;
	}

	@Autowired
	public void setDeviceDao(DeviceDao deviceDao) {
		this.deviceDao = deviceDao;
	}

	@Autowired
	public void setMessagePush(MessagePush messagePush) {
		this.messagePush = messagePush;
	}

	@Autowired
	public void setSubscriptionRelationDao(
			SubscriptionRelationDao subscriptionRelationDao) {
		this.subscriptionRelationDao = subscriptionRelationDao;
	}

	@Override
	public void updateBrand(String id, Brand brand) {
		brand.setAsciiName(toPinyin(brand.getAsciiName()));
		brandDao.updateBrand(id, brand);
	}

	@Override
	public void updateBrandState(String id, boolean enabled) {
		brandDao.updateBrandState(id, enabled);
	}
}
