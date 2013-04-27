package com.babeeta.appstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.dao.DeviceDao;
import com.babeeta.appstore.entity.Device;
import com.babeeta.appstore.service.DeviceService;

@Service("deviceService")
public class DeviceServiceImpl implements DeviceService {

	@Autowired
	private DeviceDao deviceDao;

	@Override
	public Device findDeviceByToken(String token) {
		return deviceDao.findDeviceByToken(token);
	}

}
