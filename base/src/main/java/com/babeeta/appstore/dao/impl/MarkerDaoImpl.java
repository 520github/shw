package com.babeeta.appstore.dao.impl;

import java.util.List;

import com.babeeta.appstore.dao.MarkerDao;
import com.babeeta.appstore.entity.Marker;
import com.babeeta.appstore.entity.Marker.MarkerId;
import com.google.code.morphia.query.Query;

public class MarkerDaoImpl extends BasicDaoImpl implements MarkerDao {

	@Override
	public long countDeviceMarker(String deviceId, String domain, String value) {
		Query<Marker> q = this.datastore.createQuery(Marker.class)
				.field("id.deviceId")
				.equal(deviceId).field("id.domain").equal(domain)
				.field("value").equal(value);
		return q.countAll();
	}

	@Override
	public Marker findById(MarkerId id) {
		return this.datastore.createQuery(Marker.class).field("id").equal(id)
				.get();
	}

	@Override
	public List<Marker> findMarker(String deviceId, String domain,
			String value, int offset, int limit) {
		Query<Marker> q = this.datastore.createQuery(Marker.class)
				.field("id.deviceId")
				.equal(deviceId).field("id.domain").equal(domain)
				.field("value").equal(value).order("-date").offset(offset)
				.limit(limit);
		return q.asList();
	}

	@Override
	public void removeById(MarkerId id) {
		this.datastore.delete(this.datastore.createQuery(Marker.class)
				.field("id").equal(id));
	}

	@Override
	public void save(Marker marker) {
		this.datastore.save(marker);
	}

}
