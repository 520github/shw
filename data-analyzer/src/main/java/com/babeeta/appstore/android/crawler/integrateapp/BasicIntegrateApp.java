/**
 * 
 */
package com.babeeta.appstore.android.crawler.integrateapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import com.babeeta.appstore.android.crawler.AnalyzerConstant;
import com.babeeta.appstore.android.crawler.analyzer.APKDataAnalyzer;
import com.babeeta.appstore.dao.AppDao;
import com.babeeta.appstore.dao.AppUpdateNoticeDao;
import com.babeeta.appstore.dao.DeviceDao;
import com.babeeta.appstore.entity.App;
import com.babeeta.appstore.entity.App.AppStatus;
import com.babeeta.appstore.entity.AppUpdateNotice;
import com.babeeta.appstore.entity.ApplicationMarketDetail;
import com.babeeta.appstore.entity.Device;
import com.babeeta.appstore.storage.SimpleStorageService;

/**
 * APP集成抽象APP相关信息
 * 
 * @author xuehui.miao
 * 
 */
public abstract class BasicIntegrateApp implements IntegrateApp {
	protected static Logger logger = LoggerFactory
			.getLogger(BasicIntegrateApp.class);
	private ApplicationMarketDetail appMarkteDetail;
	private Map<String, ApplicationMarketDetail> map;
	private App app;
	protected AppDao appDao;
	private DeviceDao deviceDao;
	private AppUpdateNoticeDao appUpdateNoticeDao;
	protected String market;
	protected SimpleStorageService ssService;

	public BasicIntegrateApp() {
		// appDao = new AppDaoImpl();
	}

	/**
	 * 更新或添加APP的市场详情信息
	 */
	private void addOrUpdateAppDetail() {
		appMarkteDetail.setMarket(market);

		// 取得原有APP市场应用
		App oldApp = appDao.findByAppId(app.getId());
		// 比较版本是否更新
		boolean result = compareAppVersion(oldApp);
		// 设置应用市场详情
		setApplicationMarketDetails(oldApp, result);
		// 设置应用详情
		setDetail(oldApp, result);
		if (oldApp != null) {
			oldApp.setApplicationMarketDetails(app
					.getApplicationMarketDetails());
			oldApp.setDetail(app.getDetail());
			app = oldApp;
		}
		// 设置应用更新通知
		setAppUpdateNotice(result);
	}

	/**
	 * 对新发现市场的APP版本号与原来版本号进行比较
	 * 
	 * @param oldApp
	 * @return
	 */
	private boolean compareAppVersion(App oldApp) {
		boolean result = false;
		if (oldApp == null || oldApp.getDetail() == null) {
			return result;
		}
		// 比较APK版本号
		result = appDao.compareAppVersion(oldApp.getDetail().getVersionCode(),
				appMarkteDetail.getVersionCode());
		if (result) {// 版本发生变更
			return result;
		}
		// 比较APK版本名称
		// result =
		// appDao.compareAppVersion(oldApp.getDetail().getVersionName(),
		// appMarkteDetail.getVersionName());

		return result;
	}

	/**
	 * 设置各市场应用详情
	 * 
	 * @param oldApp
	 *            原APP应用
	 */
	private void setApplicationMarketDetails(App oldApp, boolean result) {
		if (oldApp != null) {
			map = oldApp.getApplicationMarketDetails();
		}
		if (map == null) {
			map = new HashMap<String, ApplicationMarketDetail>();
		}

		if (map.containsKey(appMarkteDetail.getMarket())) {
			if (result) {
				map.put(appMarkteDetail.getMarket(), appMarkteDetail);
			} else {
				try {
					ssService.delete(APKDataAnalyzer.APKNAME,
							appMarkteDetail.getApkId());
					logger.info("delete file " + appMarkteDetail.getApkId());
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		} else {
			map.put(appMarkteDetail.getMarket(), appMarkteDetail);
		}
		app.setApplicationMarketDetails(map);
	}

	/**
	 * 设置APP应用更新的通知
	 * 
	 * @param result
	 */
	private void setAppUpdateNotice(boolean result) {
		if (!result) {// 版本号没有发生变化,不做更新通知操作
			logger.info("app[{}] no new versions.....", app.getId());
			return;
		}

		// 如果版本发生变化,要通知客户端应用更新
		// 安装该APP应用的设备列表
		List<Device> deviceList = deviceDao.findDevicesByPackageName(app
				.getId());
		if (deviceList == null || deviceList.size() < 1) {
			logger.info(
					"app[{}] have a new versions,but not client device used it......",
					app.getId());
			return;
		}

		List<AppUpdateNotice> noticeList = new ArrayList<AppUpdateNotice>();
		Date noticeDate = new Date();
		for (int i = 0; i < deviceList.size(); i++) {
			Device device = deviceList.get(i);
			if (device == null) {
				continue;
			}
			AppUpdateNotice notice = new AppUpdateNotice();
			notice.setId((new ObjectId()).toString());
			notice.setDeviceId(device.getId());
			notice.setNoticeDate(noticeDate);
			notice.setClientId(device.getClientId());
			noticeList.add(notice);
		}

		appUpdateNoticeDao.save(noticeList);
	}

	/**
	 * 设置应用详情
	 * 
	 * @param oldApp
	 *            原APP应用
	 * @param result
	 *            版本号是否发生更新
	 */
	private void setDetail(App oldApp, boolean result) {
		if (AnalyzerConstant.MARKET_TYPE_PLAY.equalsIgnoreCase(market)) {// 如果当前市场是Google
																			// Play,不覆盖应用详情信息
			return;
		}
		ApplicationMarketDetail detailTemp = null;
		if (oldApp != null) {
			detailTemp = oldApp.getDetail();
		}
		if (detailTemp == null) {//
			detailTemp = appMarkteDetail;
		}
		if (result) {// 有新版本号变更情况
			detailTemp = appMarkteDetail;
		}
		app.setDetail(detailTemp);
	}

	protected void destroy() {
		app = null;
		// this.appDao = null;
		appMarkteDetail = null;
		map = null;
		// this.market = null;
	}

	protected void saveApp() {
		if (app.getStatus() == null) {
			app.setStatus(AppStatus.unprocessed);
		}
		addOrUpdateAppDetail();
		appDao.updateByAppIdOrCreate(app);
	}

	public App getApp() {
		if (app == null) {
			app = new App();
		}
		return app;
	}

	public ApplicationMarketDetail getAppMarkteDetail() {
		if (appMarkteDetail == null) {
			appMarkteDetail = new ApplicationMarketDetail();
		}
		return appMarkteDetail;
	}

	@Autowired
	@Required
	public void setAppDao(AppDao appDao) {
		this.appDao = appDao;
	}

	@Autowired
	@Required
	public void setAppUpdateNoticeDao(AppUpdateNoticeDao appUpdateNoticeDao) {
		this.appUpdateNoticeDao = appUpdateNoticeDao;
	}

	@Autowired
	@Required
	public void setDeviceDao(DeviceDao deviceDao) {
		this.deviceDao = deviceDao;
	}

	@Autowired
	@Required
	public void setSsService(SimpleStorageService ssService) {
		this.ssService = ssService;
	}

}
