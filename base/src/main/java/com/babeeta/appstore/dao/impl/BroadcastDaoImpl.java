package com.babeeta.appstore.dao.impl;

import java.util.List;

import org.bson.types.ObjectId;

import com.babeeta.appstore.dao.BroadcastDao;
import com.babeeta.appstore.entity.Broadcast;
import com.babeeta.appstore.entity.Broadcast.Status;

public class BroadcastDaoImpl extends BasicDaoImpl implements BroadcastDao {

	@Override
	public List<Broadcast> findAll(int limit, int offset) {
		// TODO Auto-generated method stub
		return datastore.createQuery(Broadcast.class).order("-date")
				.limit(limit)
				.offset(offset).asList();
	}

	@Override
	public Broadcast findById(String id) {
		return datastore.find(Broadcast.class).field("_id").equal(id).get();
	}

	@Override
	public Broadcast save(Broadcast broadcast) {
		if (broadcast.getId() == null || broadcast.getId().trim().length() == 0) {
			broadcast.setId(new ObjectId().toString());
		}
		datastore.save(broadcast);
		return broadcast;
	}

	@Override
	public void updateStatus(String id, Status status) {
		datastore.findAndModify(
				datastore.createQuery(Broadcast.class).field("_id").equal(id),
				datastore.createUpdateOperations(Broadcast.class).set("status",
						status), false, false);
	}
}