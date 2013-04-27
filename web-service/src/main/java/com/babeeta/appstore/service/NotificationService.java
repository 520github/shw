package com.babeeta.appstore.service;

import com.babeeta.appstore.entity.Notification;
import com.babeeta.appstore.service.impl.EntityUnExistsException;
import com.babeeta.appstore.service.impl.NotificationUpdateNotAllowedException;

public interface NotificationService {
	/**
	 * 根据id获取消息
	 * 
	 * @param id
	 * @return
	 */
	public Notification findNotificationById(String id);

	// /**
	// * @param token
	// * 用户标记authToken
	// * @param since
	// * 上次请求最后的消息ID
	// * @param limit
	// * 返回数
	// * @return
	// */
	// public Map<String, Object> getNotificationByToken(String token,
	// String since,
	// int limit);

	/**
	 * 设置消息为已读
	 * 
	 * @param token
	 * @param NotificationId
	 */
	public void putAsRead(String token, String NotificationId, boolean condition)
			throws NotificationUpdateNotAllowedException,
			EntityUnExistsException;

	// /**
	// * 设置用户最后一次访问的消息的id
	// *
	// * @param token
	// * @param lastNotificationId
	// */
	// public void putLastNotificationID(String token, String
	// lastNotificationId)
	// throws EntityUnExistsException;
}
