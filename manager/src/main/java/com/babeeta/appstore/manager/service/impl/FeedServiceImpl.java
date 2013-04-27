package com.babeeta.appstore.manager.service.impl;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.dao.DeviceDao;
import com.babeeta.appstore.entity.Device;
import com.babeeta.appstore.manager.service.FeedService;
import com.babeeta.appstore.manager.service.PushService;

@Service("feedService")
public class FeedServiceImpl implements FeedService {

	private static final Logger logger = LoggerFactory
			.getLogger(FeedServiceImpl.class);
	private DeviceDao deviceDao;
	private PushService pushService;

	public DeviceDao getDeviceDao() {
		return deviceDao;
	}

	public PushService getPushService() {
		return pushService;
	}

	@Autowired
	public void setDeviceDao(DeviceDao deviceDao) {
		this.deviceDao = deviceDao;
	}

	@Autowired
	public void setPushService(PushService pushService) {
		this.pushService = pushService;
	}

	@Override
	public void work() {
		Map<String, Date> deviceMap = pushService.getInactiveDevices();
		for (Map.Entry<String, Date> entry : deviceMap.entrySet()) {
			Device device = deviceDao.findDeviceByToken(entry.getKey());
			if (device != null) {

				device.setToken(null);
			} else {
				logger.info("device[{}] not exist",
						new Object[] { entry.getKey() });
			}

		}

	}
}
