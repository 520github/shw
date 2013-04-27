package com.babeeta.appstore.manager.service.impl;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.dao.BrandDao;
import com.babeeta.appstore.dao.NotificationDao;
import com.babeeta.appstore.dao.SubscriptionRelationDao;
import com.babeeta.appstore.dao.impl.EstimatableIterator;
import com.babeeta.appstore.entity.Brand;
import com.babeeta.appstore.entity.Device;
import com.babeeta.appstore.entity.Notification;
import com.babeeta.appstore.entity.SubscriptionRelation;
import com.babeeta.appstore.manager.service.NotificationService;

@Service("notificationService")
public class NotificationServiceImpl implements NotificationService {
	private final static Logger logger = LoggerFactory
			.getLogger(NotificationServiceImpl.class);
	private BrandDao brandDao;
	private NotificationDao notificationDao;
	private SubscriptionRelationDao subscriptionRelationDao;

	// @Override
	// public long countBadge(Device device) {
	// return notificationDao.findNormalUnreadCount(device.getToken(),
	// device.getLastNotificationId());
	// }

	@Override
	public void createBestNotification(Device device, long newCount) {
		String collapseKey = "daily best";
		Notification n = notificationDao.findByOwnerIdAndCollapseKey(
				device.getToken(), collapseKey);
		if (n == null) {
			n = new Notification();
			n.setAction("view://app/best");
			n.setCollapseKey(collapseKey);
			n.setDate(new Date());
			n.setId(new ObjectId().toString());
			n.setOwnerId(device.getToken());
			n.setStatus(Notification.Status.normal);
			n.setSubject("今日推荐" + newCount + "个应用。");
		} else {
			notificationDao.deleteById(n.getId());// 删掉原来的消息体，以便更换id
			n.setId(new ObjectId().toString());
			n.setDate(new Date());
		}
		if (newCount > 0) {
			n.setSubject("今日推荐" + newCount + "个应用。");
			n.setRead(null);
			n.setStatus(Notification.Status.normal);
		} else {
			// 今日无更新，则需要把该用户的消息的collapseKey为"daliy best"的通知置为deleted
			n.setRead(null);
			n.setStatus(Notification.Status.deleted);
		}
		logger.debug("Device token:[{}] has 'daliy best' count:[{}].",
				device.getToken(), newCount);
		notificationDao.save(n);
	}

	@Override
	public void createSubscriptionNotification(Device device) {
		String collapseKey = "brand subscription";
		List<SubscriptionRelation> relations = subscriptionRelationDao
				.getSubscriptionRelationListByToken(device.getToken());
		long count = 0;
		for (SubscriptionRelation subscriptionRelation : relations) {
			Brand brand = brandDao.findById(subscriptionRelation.getBrandId());
			if (brand != null
					&& brand.getLastModified() != null
					&& brand.getLastModified().after(
							subscriptionRelation.getLastVisitDate())) {
				count++;
			}
		}
		Notification n = notificationDao.findByOwnerIdAndCollapseKey(
				device.getToken(), collapseKey);
		if (n == null) {
			n = new Notification();
			n.setAction("view://brand-subscription");
			n.setCollapseKey(collapseKey);
			n.setDate(new Date());
			n.setId(new ObjectId().toString());
			n.setOwnerId(device.getToken());
			n.setStatus(Notification.Status.normal);
			n.setSubject("关注品牌更新" + count + "个。");
		} else {
			notificationDao.deleteById(n.getId());// 删掉原来的消息体，以便更换id
			n.setId(new ObjectId().toString());
			n.setDate(new Date());
		}
		if (count > 0) {
			n.setSubject("关注品牌更新" + count + "个。");
			n.setRead(null);
			n.setStatus(Notification.Status.normal);
		} else {
			// 今日无更新，则需要把该用户的消息的collapseKey为"daliy best"的通知置为deleted
			n.setRead(null);
			n.setStatus(Notification.Status.deleted);
		}
		logger.debug("Device token:[{}] has 'brand subscription' count:[{}].",
				device.getToken(), count);
		notificationDao.save(n);

	}

	@Override
	public EstimatableIterator<Notification> iteratorByCollapseKey(
			String collapseKey) {
		return notificationDao.iteratorByCollapseKey(collapseKey);
	}

	@Override
	public EstimatableIterator<Notification> iteratorByCollapseKey(
			String collapseKey, String since) {
		return notificationDao.iteratorByCollapseKey(collapseKey, since);
	}

	@Override
	public Notification save(Notification notification) {
		notificationDao.deleteByOwnerIdAndCollapseKey(
				notification.getOwnerId(), notification.getCollapseKey());
		return notificationDao.save(notification);
	}

	@Autowired
	@Required
	public void setBrandDao(BrandDao brandDao) {
		this.brandDao = brandDao;
	}

	@Autowired
	@Required
	public void setNotificationDao(NotificationDao notificationDao) {
		this.notificationDao = notificationDao;
	}

	@Autowired
	@Required
	public void setSubscriptionRelationDao(
			SubscriptionRelationDao subscriptionRelationDao) {
		this.subscriptionRelationDao = subscriptionRelationDao;
	}

}
