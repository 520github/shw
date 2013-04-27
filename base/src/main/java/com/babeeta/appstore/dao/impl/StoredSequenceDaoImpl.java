package com.babeeta.appstore.dao.impl;

import com.babeeta.appstore.dao.SequenceDao;
import com.babeeta.appstore.entity.StoredSequence;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

/**
 * 增长序列dao实现
 * 
 * @author chongf
 * 
 */
public class StoredSequenceDaoImpl extends BasicDaoImpl implements
		SequenceDao<Integer> {

	@Override
	public Integer nextSequence(String cellName) {
		Query<StoredSequence> q = datastore.createQuery(StoredSequence.class)
				.filter("_id", cellName);
		UpdateOperations<StoredSequence> o = datastore.createUpdateOperations(
				StoredSequence.class).inc("value");
		StoredSequence e = datastore.findAndModify(q, o);
		if (e == null) {
			// update(increase 0,no changes), if not found create it
			datastore.updateFirst(
					q,
					datastore.createUpdateOperations(StoredSequence.class).inc(
							"value", 0), true);
			// 为避免极限情况，第一次插入后再去重新获取
			return nextSequence(cellName);
		}
		return e.getValue();
	}
}
