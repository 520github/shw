package com.babeeta.appstore.manager.service.impl;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.dao.AppDao;
import com.babeeta.appstore.dao.BestAppDao;
import com.babeeta.appstore.entity.App;
import com.babeeta.appstore.entity.BestApp;
import com.babeeta.appstore.manager.service.BestAppService;

@Service("bestAppService")
public class BestAppServiceImpl implements BestAppService {

	private BestAppDao bestAppDao;
	private AppDao appDao;

	@Override
	public void delete(String id) {
		bestAppDao.delete(id);
	}

	@Override
	public List<BestApp> findAList() {
		return bestAppDao.findAList();
	}

	@Override
	public void save(BestApp bestapp) {
		App app = appDao.findByAppId(bestapp.getId());
		if (app != null) {
			bestapp.setApp(app);
			bestAppDao.save(bestapp);
		}

	}

	@Override
	public void saveAppBest() {
		Iterator<BestApp> i = bestAppDao.findAll();
		if (i != null) {
			while (i.hasNext()) {
				BestApp b = i.next();
				App app = b.getApp();
				app.setBest(true);
				app.setBestLastModified(Calendar.getInstance().getTime());
				appDao.save(app);
				bestAppDao.delete(b.getId());
			}
		}
	}

	@Autowired
	public void setAppDao(AppDao appDao) {
		this.appDao = appDao;
	}

	@Autowired
	public void setBestAppDao(BestAppDao bestAppDao) {
		this.bestAppDao = bestAppDao;
	}

}
