package com.babeeta.appstore.dao.impl;

import java.util.Iterator;
import java.util.List;

import com.babeeta.appstore.dao.BestAppDao;
import com.babeeta.appstore.entity.BestApp;

public class BestAppDaoImpl extends BasicDaoImpl implements BestAppDao {

	@Override
	public void delete(String id) {
		datastore
				.delete(datastore.createQuery(BestApp.class).filter("_id", id));

	}

	@Override
	public List<BestApp> findAList() {
		return datastore.createQuery(BestApp.class).order("-createTime")
				.asList();
	}

	@Override
	public Iterator<BestApp> findAll() {
		return datastore.createQuery(BestApp.class).order("-createTime")
				.iterator();
	}

	@Override
	public void save(BestApp bestapp) {
		datastore.save(bestapp);

	}

}
