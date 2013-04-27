package com.babeeta.sheepcounter.manager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babeeta.sheepcounter.dao.UrlDao;
import com.babeeta.sheepcounter.entity.Url;
import com.babeeta.sheepcounter.manager.service.SheepCounterService;

@Service("sheepCounterService")
public class SheepCounterServiceImpl implements SheepCounterService {

	private UrlDao urlDao;

	@Override
	public Url saveUrl(Url url) {
		return urlDao.save(url);
	}

	@Autowired
	public void setUrlDao(UrlDao urlDao) {
		this.urlDao = urlDao;
	}

}
