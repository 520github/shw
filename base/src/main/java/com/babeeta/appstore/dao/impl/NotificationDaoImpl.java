package com.babeeta.appstore.dao.impl;

import java.util.List;

import org.bson.types.ObjectId;

import com.babeeta.appstore.dao.NotificationDao;
import com.babeeta.appstore.entity.Notification;
import com.google.code.morphia.query.Query;

public class NotificationDaoImpl extends BasicDaoImpl implements
		NotificationDao {

	@Override
	public void deleteById(String id) {
		datastore.delete(this.datastore
				.createQuery(Notification.class).field("id")
				.equal(id));
	}

	@Override
	public void deleteByOwnerIdAndCollapseKey(String ownerId, String collapseKey) {
		Query<Notification> query = this.datastore
				.createQuery(Notification.class).field("ownerId")
				.equal(ownerId).field("collapseKey").equal(collapseKey);
		this.datastore.delete(query);
	}

	@Override
	public Notification findById(String id) {
		return this.datastore.createQuery(Notification.class).filter("id", id)
				.get();
	}

	@Override
	public Notification findByOwnerIdAndCollapseKey(String ownerId,
			String collapseKey) {
		Query<Notification> query = this.datastore
				.createQuery(Notification.class).field("ownerId")
				.equal(ownerId).field("collapseKey").equal(collapseKey);
		return query.get();
	}

	@Override
	public List<Notification> findByOwnerIdAndSince(String ownerId,
			String since, int limit) {
		Query<Notification> query = this.datastore
				.createQuery(Notification.class).field("ownerId")
				.equal(ownerId);
		if (since != null) {
			query.filter("id >", since);
		}
		query.limit(limit);
		return query.asList();
	}

	@Override
	public long findNormalUnreadCount(String ownerId, String since) {
		Query<Notification> query = this.datastore
				.createQuery(Notification.class).field("ownerId")
				.equal(ownerId).field("status")
				.equal(Notification.Status.normal)
				.field("read").equal(null);
		if (since != null) {
			query.filter("id >", since);
		}
		return query.countAll();
	}

	@Override
	public EstimatableIterator<Notification> iteratorByCollapseKey(
			String collapseKey) {
		return estimatableIterator(this.datastore
				.createQuery(Notification.class)
				.field("collapseKey").equal(collapseKey)
				.order("-_id"));
	}

	@Override
	public EstimatableIterator<Notification> iteratorByCollapseKey(
			String collapseKey,
			String since) {
		return estimatableIterator(this.datastore
				.createQuery(Notification.class)
				.field("collapseKey").equal(collapseKey)
				.field("_id").greaterThan(since)
				.order("-_id"));
	}

	@Override
	public Notification save(Notification n) {
		if (n.getId() == null || n.getId().trim().length() == 0) {
			n.setId(new ObjectId().toString());
		}
		this.datastore.save(n);
		return n;
	}

}
