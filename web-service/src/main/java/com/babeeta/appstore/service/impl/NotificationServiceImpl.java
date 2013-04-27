package com.babeeta.appstore.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.dao.NotificationDao;
import com.babeeta.appstore.entity.Notification;
import com.babeeta.appstore.service.DeviceRegistService;
import com.babeeta.appstore.service.NotificationService;

@Service("notificationService")
public class NotificationServiceImpl implements NotificationService {
	private final static Logger logger = LoggerFactory
			.getLogger(NotificationServiceImpl.class);

	private DeviceRegistService deviceRegistService;
	private NotificationDao notificationDao;

	@Override
	public Notification findNotificationById(String id) {
		return notificationDao.findById(id);
	}

	// @Override
	// public Map<String, Object> getNotificationByToken(String token,
	// String since, int limit) {
	// List<Notification> list = notificationDao.findByOwnerIdAndSince(
	// token, since,
	// limit);
	// Device device = deviceRegistService.findDeviceByToken(token);
	// long count = 0;
	// if (device != null) {
	// count = notificationDao.findNormalUnreadCount(token,
	// device.getLastNotificationId());
	// }
	// Map<String, Object> map = new HashMap<String, Object>();
	// map.put("badge", count);
	// map.put("content", list);
	// return map;
	// }

	@Override
	public void putAsRead(String token, String notificationId, boolean condition)
			throws NotificationUpdateNotAllowedException,
			EntityUnExistsException {
		Notification n = notificationDao.findById(notificationId);
		if (n == null) {
			throw new EntityUnExistsException();
		}
		if (!n.getOwnerId().equals(token)) {// 不能修改不属于自己的通知
			logger
					.info(
							"Notification id:[{}] couldn't update by Device token:[{}]",
							notificationId, token);
			throw new NotificationUpdateNotAllowedException();
		}
		n.setRead(condition ? new Date() : null);
		notificationDao.save(n);
	}

	// @Override
	// public void putLastNotificationID(String token, String
	// lastNotificationId)
	// throws EntityUnExistsException {
	// Device device = deviceRegistService.findDeviceByToken(token);
	// if (device == null) {
	// throw new EntityUnExistsException();
	// }
	// if (device != null) {
	// device.setLastNotificationId(lastNotificationId);
	// deviceRegistService.saveDevice(device);
	// }
	// }

	@Autowired
	@Required
	public void setDeviceRegistService(DeviceRegistService deviceRegistService) {
		this.deviceRegistService = deviceRegistService;
	}

	@Autowired
	@Required
	public void setNotificationDao(NotificationDao notificationDao) {
		this.notificationDao = notificationDao;
	}

}