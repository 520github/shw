package com.babeeta.appstore.manager.service.impl;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.dao.DeviceDao;
import com.babeeta.appstore.dao.impl.EstimatableIterator;
import com.babeeta.appstore.entity.Device;
import com.babeeta.appstore.manager.service.DeviceService;

@Service("deviceService")
public class DeviceServiceImpl implements DeviceService {

	private DeviceDao deviceDao;

	@Override
	public long countByLastDeviceId(String lastDeviceId) {
		return deviceDao.countDeviceByLastDeviceId(lastDeviceId);
	}

	@Override
	public long countDevice() {
		return deviceDao.countDevice();
	}

	@Override
	public Device getDeviceByToken(String token) {
		// TODO Auto-generated method stub
		return deviceDao.findDeviceByToken(token);
	}

	@Override
	public EstimatableIterator<Device> iteratorByPush() {
		// TODO Auto-generated method stub
		return deviceDao.iteratorByPush();
	}

	@Override
	public EstimatableIterator<Device> iteratorGreaterThanDeviceId(
			String lastDeviceId) {
		return deviceDao.iteratorGreaterThanLastDeviceId(lastDeviceId);
	}

	@Override
	public Iterator<Device> iteratorLessThanOrEqDeviceId(String lastDeviceId) {
		return deviceDao.iteratorLessThanDeviceId(lastDeviceId);
	}

	@Autowired
	@Required
	public void setDeviceDao(DeviceDao deviceDao) {
		this.deviceDao = deviceDao;
	}

}
