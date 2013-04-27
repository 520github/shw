package com.babeeta.appstore.dao.impl;

import java.util.Iterator;
import java.util.List;

import com.babeeta.appstore.dao.DeviceDao;
import com.babeeta.appstore.entity.AppTrack.enumAppSource;
import com.babeeta.appstore.entity.Device;
import com.google.code.morphia.query.Query;

public class DeviceDaoImpl extends BasicDaoImpl implements DeviceDao {

	@Override
	public long countDevice() {
		return datastore.createQuery(Device.class).countAll();
	}

	@Override
	public long countDeviceByLastDeviceId(String deviceId) {
		return datastore.createQuery(Device.class).field("id")
				.lessThanOrEq(deviceId).countAll();
	}

	@Override
	public Device findById(String deviceId) {
		return datastore.createQuery(Device.class).filter("_id", deviceId)
				.get();

	}

	@Override
	public Device findDeviceByImei(String imei) {
		return datastore.createQuery(Device.class).filter("imei", imei).get();
	}

	@Override
	public Device findDeviceByToken(String token) {
		return datastore.createQuery(Device.class).filter("token =", token)
				.get();
	}

	@Override
	public List<Device> findDevicesByPackageName(String packageName) {
		return datastore.createQuery(Device.class).
				field("apps.packageName").equal(packageName).
				field("apps.appSource").equal(enumAppSource.coralBay).asList();
	}

	@Override
	public EstimatableIterator<Device> iteratorByPush() {
		return estimatableIterator(datastore.createQuery(Device.class));
	}

	@Override
	public EstimatableIterator<Device> iteratorGreaterThanLastDeviceId(
			String lastDeviceId) {
		Query<Device> q = datastore.createQuery(Device.class);
		if (lastDeviceId != null) {
			q = q.field("id").greaterThan(lastDeviceId);
		}

		return estimatableIterator(q);
	}

	@Override
	public Iterator<Device> iteratorLessThanDeviceId(String lastDeviceId) {
		Query<Device> q = datastore.createQuery(Device.class);
		if (lastDeviceId != null) {
			q = q.field("id").lessThanOrEq(lastDeviceId);
		}
		return q.iterator();
	}

	@Override
	public void saveDevice(Device device) {
		datastore.save(device);
	}

}
