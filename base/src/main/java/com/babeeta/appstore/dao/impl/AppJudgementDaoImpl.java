package com.babeeta.appstore.dao.impl;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.babeeta.appstore.dao.AppJudgementDao;
import com.babeeta.appstore.entity.AppJudgement;
import com.google.code.morphia.query.Query;
import com.google.common.base.Strings;

public class AppJudgementDaoImpl extends BasicDaoImpl implements
		AppJudgementDao {

	@Override
	public long countAll(String keyword) {
		return datastore.createQuery(AppJudgement.class).countAll();
	}

	@Override
	public AppJudgement findById(String id) {
		return datastore.createQuery(AppJudgement.class).field("id").equal(id)
				.get();
	}

	@Override
	public List<AppJudgement> findByKeyword(String keyword, int limit,
			int offset) {
		Query<AppJudgement> q = datastore.createQuery(AppJudgement.class);
		if (!Strings.isNullOrEmpty(keyword)) {
			q.field("title").contains(keyword);
		}
		q.order("-date").limit(limit).offset(offset);
		return q.asList();
	}

	@Override
	public List<AppJudgement> findHistory() {
		Query<AppJudgement> query = datastore.createQuery(AppJudgement.class);
		return query.field("available").equal(true).field("end")
				.lessThan(new Date()).order("-end").asList();

	}

	@Override
	public List<AppJudgement> findOngoing() {
		Query<AppJudgement> query = datastore.createQuery(AppJudgement.class);
		return query.field("available").equal(true).field("end")
				.greaterThan(new Date()).order("-begin").asList();
	}

	@Override
	public void save(AppJudgement appJudgement) {
		if (appJudgement.getId() == null) {
			appJudgement.setId(new ObjectId().toString());
			appJudgement.setDate(new Date());
		}
		appJudgement.setLastModify(new Date());
		datastore.save(appJudgement);
	}

}
