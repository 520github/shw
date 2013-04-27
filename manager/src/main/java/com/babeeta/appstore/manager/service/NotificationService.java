package com.babeeta.appstore.manager.service;

import com.babeeta.appstore.dao.impl.EstimatableIterator;
import com.babeeta.appstore.entity.Device;
import com.babeeta.appstore.entity.Notification;

public interface NotificationService {
	// /**
	// * 查询该用户的更新数
	// *
	// * @param device
	// */
	// public long countBadge(Device device);

	/**
	 * 生成最新推荐的推荐通知
	 * 
	 * @param device
	 * @param since
	 */
	public void createBestNotification(Device device, long newCount);

	/**
	 * 生成品牌订阅的更新通知
	 * 
	 * @param device
	 */
	public void createSubscriptionNotification(Device device);

	public EstimatableIterator<Notification> iteratorByCollapseKey(String action);

	public EstimatableIterator<Notification> iteratorByCollapseKey(
			String action,
			String since);

	public Notification save(Notification notification);
}
