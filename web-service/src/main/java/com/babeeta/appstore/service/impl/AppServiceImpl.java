package com.babeeta.appstore.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.dao.AppDao;
import com.babeeta.appstore.dao.GroupItemDao;
import com.babeeta.appstore.entity.App;
import com.babeeta.appstore.entity.Catalog;
import com.babeeta.appstore.entity.GroupItem;
import com.babeeta.appstore.service.AppService;

@Service("appService")
public class AppServiceImpl implements AppService {

	private AppDao appDao;
	private GroupItemDao groupItemDao;

	@Override
	public long countAllBest() {
		return appDao.countBest();
	}

	@Override
	public long countAllPublished() {
		App.AppStatus[] status = { App.AppStatus.published };
		return appDao.count(null, status);
	}

	@Override
	public long countPublishedByCatalogName(String Catalog) {
		return appDao.countPublishedByCatalogName(Catalog);
	}

	@Override
	public int countSearch(String search) {
		return appDao.countSearch(search);
	}

	@Override
	public List<App> getAppByCatalogOrderByVote(String catalogName, int offset,
			int limit) {
		// TODO Auto-generated method stub
		if (limit > 100) {
			limit = 100;
		}

		return appDao.findAppByCatalogOrderByVote(catalogName, offset, limit);
	}

	@Override
	public App getAppByRandom(double randomNum) {
		return appDao.getAppByRandom(randomNum);
	}

	@Override
	public List<App> getAppWithBest(int offset, int limit) {
		return appDao.getAppWithBest(offset, limit);
	}

	@Override
	public List<App> getByCatalog(Catalog catalog, int offset,
			int limit) {
		return appDao.findPublishedByCatalogName(catalog.getName(), offset,
				limit);
	}

	@Override
	public List<App> getByCatalogAndGroup(String catalogName,
			String brandName, int offset, int limit) {
		// TODO Auto-generated method stub
		return appDao.findPublishedByCatalogAndGroup(catalogName, brandName,
				offset, limit);
	}

	@Override
	public List<GroupItem> getByCatalogGroupByBrand(String catalogName,
			int offset, int limit) {
		return groupItemDao.findByCatalog(catalogName, offset, limit);
	}

	@Override
	public App getById(String appId) {
		return appDao.findByAppId(appId);
	}

	@Override
	public List<App> getByIds(List<String> ids) {
		return appDao.findByIds(ids);
	}

	@Override
	public App getPublishedById(String appId) {
		return appDao.findPublishedById(appId);
	}

	@Override
	public List<App> getPublishedByIds(List<String> ids) {
		return appDao.findPublishedByIds(ids);
	}

	@Override
	public List<App> search(String search, int offset, int limit) {
		return appDao.search(search, offset, limit);
	}

	@Autowired
	@Required
	public void setAppDao(AppDao appDao) {
		this.appDao = appDao;
	}

	@Autowired
	@Required
	public void setBrandItemDao(GroupItemDao groupItemDao) {
		this.groupItemDao = groupItemDao;
	}

	@Override
	public List<App> topApp(String condition, int offset, int limit) {
		List<App> list;
		if ("hot".equals(condition)) {
			list = appDao.findFrontPublishedByScore(offset, limit);
		} else {
			list = appDao.findFrontPublishedByLastModified(offset, limit);
		}
		return list;
	}

}
