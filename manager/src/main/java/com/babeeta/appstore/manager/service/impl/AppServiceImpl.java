package com.babeeta.appstore.manager.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.dao.AppDao;
import com.babeeta.appstore.dao.CatalogDao;
import com.babeeta.appstore.dao.EntityNotFoundException;
import com.babeeta.appstore.dao.GroupItemDao;
import com.babeeta.appstore.entity.App;
import com.babeeta.appstore.entity.App.AppStatus;
import com.babeeta.appstore.entity.Catalog;
import com.babeeta.appstore.entity.GroupItem;
import com.babeeta.appstore.manager.service.AppService;
import com.babeeta.appstore.manager.service.LockConflictException;

/**
 * 应用服务
 * 
 * @author leon
 * 
 */
@Service("appService")
public class AppServiceImpl implements AppService {
	private static final Logger logger = LoggerFactory
			.getLogger(AppServiceImpl.class);

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

	private AppDao appDao;
	private CatalogDao catalogDao;

	private GroupItemDao groupItemDao;

	private void createGroupItem(App app, String catalog, String group) {
		GroupItem gi = new GroupItem();
		gi.setId(new ObjectId().toString());
		gi.setGroup(group);
		gi.setCatalog(catalog);
		gi.setHasMoreApp(false);
		gi.setBrandAsciiName(app.getGroupAsciiName());
		List<App> apps = new ArrayList<App>();
		apps.add(app);
		gi.setApps(apps);
		groupItemDao.save(gi);
	}

	private void hockGroupItem(App app) {
		HashMap<String, String> uniqueMap = new HashMap<String, String>();// catalog+brand为唯一key，用来标记已经处理过的brandItem记录
		List<GroupItem> gis = groupItemDao.findByAppId(app.getId());
		if (gis != null) {
			/**
			 * 此逻辑是处理原来groupItem中含有此app的记录
			 * 如果此app的分类或分组信息已经修改，则从groupItem中apps属性中移除
			 * 如果此时的GroupItem的apps为空，则将此记录删除
			 */
			for (GroupItem gi : gis) {
				reviewGroupItem(gi);
				uniqueMap.put(gi.getCatalog().concat(gi.getGroup()), null);
			}
		}
		/**
		 * 此逻辑是为了检测app进行了分类和品牌的修改，并把相应的修改同步到GroupItem的记录中 如果相应的GroupItem记录不存在则新建
		 */
		String group = app.getGroup();
		for (int i = 0; i < app.getCatalog().size(); i++) {
			String catalog = app.getCatalog().get(i);
			if (group == null || group.length() == 0) {
				continue;
			}
			if (!uniqueMap.containsKey(catalog.concat(group))) {
				GroupItem gi = groupItemDao.findByCatalogAndGroup(catalog,
						group);
				Catalog orgCatalog = catalogDao.findCatalog(catalog);// 从catalog表中取出相应catalog，判断是否存在(核对数据正确性，删除冗余数据)
				if (gi == null && orgCatalog != null
						&& app.getStatus().equals(App.AppStatus.published)) {
					createGroupItem(app, catalog, group);
				} else if (gi != null) {
					reviewGroupItem(gi);
				}
				uniqueMap.put(catalog.concat(group), null);
			}
		}
	}

	private void reviewGroupItem(GroupItem gi) {
		List<App> apps = appDao.findPublishedByCatalogAndGroup(gi.getCatalog(),
				gi.getGroup(), 0, 4);
		boolean b = false;
		if (apps == null || apps.size() == 0) {
			groupItemDao
					.deleteByCatalogAndGroup(gi.getCatalog(), gi.getGroup());
			return;
		}
		if (apps.size() == 4) {
			apps.remove(3);
			b = true;
		}
		gi.setApps(apps);
		gi.setHasMoreApp(b);
		groupItemDao.save(gi);
	}

	private void updateStatus(String id, AppStatus status) {
		App app = appDao.findByAppId(id);
		if (app != null) {
			app.setLastModified(new Date());
			appDao.save(app.setStatus(status));
		} else {
			throw new EntityNotFoundException(App.class, id);
		}
		if (app.getStatus().equals(AppStatus.published)) {
			appDao.addIndex(app);// 发布后，更新信息到索引文件中
		}
		hockGroupItem(app);
	}

	@Override
	public void approve(String id) {
		updateStatus(id, AppStatus.published);
	}

	@Override
	public long countAll(String appId, String catalog, String language,
			String keyword) {
		return appDao.count(appId, catalog, language, keyword);
	}

	@Override
	public long countAwaiting(String search) {
		return appDao.count(search, new AppStatus[] { AppStatus.awaiting });
	}

	@Override
	public long countProcessed(String search, String catalog) {
		return appDao.count(search, catalog, new AppStatus[] {
				AppStatus.awaiting, AppStatus.refused });
	}

	@Override
	public long countPublished(String search, String catalog) {
		return appDao.count(search, catalog,
				new AppStatus[] { AppStatus.published });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.babeeta.appstore.manager.service.AppService#countUnprocessed()
	 */
	@Override
	public long countUnprocessed(String search) {
		return appDao.count(search, new AppStatus[] { AppStatus.unprocessed });
	}

	@Override
	public void disable(String id) {
		App app = appDao.findByAppId(id);
		app.setStatus(AppStatus.disabled);
		app.setBest(false);
		appDao.save(app);
		appDao.deleteIndex(id);// 下架的时候从索引中移除
		hockGroupItem(app);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.babeeta.appstore.manager.service.AppService#find(int)
	 */
	@Override
	public App find(String id) {
		return appDao.findByAppId(id);
	}

	@Override
	public List<App> findAll(String appId, String catalog, String language,
			String keyword, int offset, int limit) {

		return appDao.findAll(appId, catalog, language, keyword, offset, limit);
	}

	@Override
	public List<App> findAwaiting(String search, int offset, int limit) {
		return appDao.find(search, new AppStatus[] { AppStatus.awaiting },
				offset, limit);
	}

	@Override
	public List<App> findProcessed(String search, String catalog, int offset,
			int limit) {
		return appDao.find(search, catalog, new AppStatus[] {
				AppStatus.awaiting, AppStatus.refused }, offset, limit);
	}

	@Override
	public List<App> findPublished(String search, String catalog, int offset,
			int limit) {
		return appDao.find(search, catalog, new AppStatus[] {
				AppStatus.published, AppStatus.disabled }, offset, limit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.babeeta.appstore.manager.service.impl.AppService#findUnprocessed(
	 * long, int)
	 */
	@Override
	public List<App> findUnprocessed(String search, int offset, int limit) {
		return appDao.find(search, new AppStatus[] { AppStatus.unprocessed },
				offset, limit);
	}

	public AppDao getAppDao() {
		return appDao;
	}

	@Override
	public App lock(String id, String username) {
		App app = appDao.findByAppId(id);
		if (!app.getStatus().equals(App.AppStatus.unprocessed)) {
			return app;
		}
		String owner = appDao.lock(id, username);
		if (username.equals(owner)) {
			return app;
		} else {
			throw new LockConflictException(owner);
		}
	}

	@Override
	public void markRemove(String id) {
		App org = appDao.findByAppId(id);
		if (org != null) {
			org.setStatus(AppStatus.deleted);
			org.setBest(false);// 下架时，抹去精品推荐的属性
			org.setBestLastModified(null);// 抹去最后精品推荐时间
			appDao.save(org);
			appDao.deleteIndex(id);// 删除时从索引中移除
		} else {
			logger.warn("Cannot find app[{}] in db.", id);
		}
		hockGroupItem(org);
	}

	@Override
	public void published(String id) {
		updateStatus(id, AppStatus.published);
	}

	@Override
	public void refuse(String id) {
		updateStatus(id, AppStatus.refused);
	}

	@Override
	public void release(String id, String username) {
		App app = appDao.findByAppId(id);
		if (app != null && app.getStatus().equals(App.AppStatus.unprocessed)) {
			appDao.release(id, username);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.babeeta.appstore.manager.service.AppService#save(com.babeeta.appstore
	 * .entity.App)
	 */
	@Override
	public void save(App app) {
		if (app.getAsciiName() == null) {
			app.setAsciiName(toPinyin(app.getDetail()
					.getName()));
		}
		if (app.getGroup() != null) {
			app.setGroupAsciiName(toPinyin(app.getGroup()));
		}
		app.setLastModified(new Date());
		appDao.save(app);
		hockGroupItem(app);
	}

	@Autowired
	@Required
	public void setAppDao(AppDao appDao) {
		this.appDao = appDao;
	}

	@Autowired
	@Required
	public void setCatalogDao(CatalogDao catalogDao) {
		this.catalogDao = catalogDao;
	}

	@Autowired
	@Required
	public void setGroupItemDao(GroupItemDao groupItemDao) {
		this.groupItemDao = groupItemDao;
	}

	@Override
	public void toggleSave(App app) {
		appDao.save(app);
	}

	@Override
	public void updateNote(String id, String note) {
		App app = appDao.findByAppId(id);
		if (app != null) {
			app.setNote(note);
			appDao.save(app);
		} else {
			throw new EntityNotFoundException(App.class, id);
		}
	}
}
