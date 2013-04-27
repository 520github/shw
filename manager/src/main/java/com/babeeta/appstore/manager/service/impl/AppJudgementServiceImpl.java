package com.babeeta.appstore.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.dao.AppJudgementDao;
import com.babeeta.appstore.entity.AppJudgement;
import com.babeeta.appstore.manager.service.AppJudgementService;

@Service("appJudgementService")
public class AppJudgementServiceImpl implements AppJudgementService {

	private AppJudgementDao appJudgementDao;

	@Override
	public long countAll(String keyword) {
		return appJudgementDao.countAll(keyword);
	}

	@Override
	public List<AppJudgement> findAppJudgements(String keyword, int limit,
			int offset) {
		return appJudgementDao.findByKeyword(keyword, limit, offset);
	}

	@Override
	public AppJudgement findById(String id) {
		return appJudgementDao.findById(id);
	}

	@Override
	public void save(AppJudgement appJudgement) {
		appJudgementDao.save(appJudgement);

	}

	@Required
	@Autowired
	public void setAppJudgementDao(AppJudgementDao appJudgementDao) {
		this.appJudgementDao = appJudgementDao;
	}

}
