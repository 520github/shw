package com.babeeta.appstore.dao.impl;

import java.util.List;

import com.babeeta.appstore.dao.MarkerCounterDao;
import com.babeeta.appstore.entity.MarkerCounter;
import com.babeeta.appstore.entity.MarkerCounter.MarkerCounterId;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

public class MarkerCounterDaoImpl extends BasicDaoImpl implements
		MarkerCounterDao {

	@Override
	public long countById(MarkerCounterId id, String value) {
		Query<MarkerCounter> q = this.datastore
				.createQuery(MarkerCounter.class)
				.field("id").equal(id);
		MarkerCounter mc = q.get();
		long ret = 0;
		if (mc != null) {
			ret = mc.getCounter().get(value);
		}
		return ret;
	}

	@Override
	public void decreaseCounter(MarkerCounterId id, String value) {
		Query<MarkerCounter> q = this.datastore
				.createQuery(MarkerCounter.class)
				.field("id").equal(id);
		UpdateOperations<MarkerCounter> ops = this.datastore
				.createUpdateOperations(MarkerCounter.class).dec(
						"counter." + value);
		this.datastore.findAndModify(q, ops, false, true);

	}

	@Override
	public List<MarkerCounter> findByEntity(String entityId) {
		return this.datastore
				.createQuery(MarkerCounter.class)
				.field("id.entityId").equal(entityId).asList();
	}

	@Override
	public MarkerCounter findById(MarkerCounterId id) {
		return this.datastore
				.createQuery(MarkerCounter.class)
				.field("id").equal(id).get();
	}

	@Override
	public void increaseCounter(MarkerCounterId id, String value) {
		Query<MarkerCounter> q = this.datastore
				.createQuery(MarkerCounter.class)
				.field("id").equal(id);
		UpdateOperations<MarkerCounter> ops = this.datastore
				.createUpdateOperations(MarkerCounter.class).inc(
						"counter." + value);
		this.datastore.findAndModify(q, ops, false, true);
	}

}
