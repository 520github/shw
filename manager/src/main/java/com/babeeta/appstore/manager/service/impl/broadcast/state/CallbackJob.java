package com.babeeta.appstore.manager.service.impl.broadcast.state;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.babeeta.appstore.dao.BroadcastDao;
import com.babeeta.appstore.dao.impl.EstimatableIterator;
import com.babeeta.appstore.entity.Broadcast;
import com.babeeta.appstore.entity.Device;
import com.babeeta.appstore.entity.Notification;
import com.babeeta.appstore.manager.service.DeviceService;
import com.babeeta.appstore.manager.service.NotificationService;
import com.babeeta.appstore.manager.service.impl.broadcast.Job;

class CallbackJob extends AbstractJob<String> implements Job {
	private static final Logger logger = LoggerFactory
			.getLogger(CallbackJob.class);
	private final BroadcastDao broadcastDao;
	private final AtomicInteger counter = new AtomicInteger();
	private final DeviceService deviceSerice;
	private final NotificationService notificationService;
	final Broadcast broadcast;

	CallbackJob(Broadcast broadcast, NotificationService notificationService,
			BroadcastDao broadcastDao, DeviceService deviceService) {
		super(broadcast.getId());
		this.broadcast = broadcast;
		this.notificationService = notificationService;
		this.broadcastDao = broadcastDao;
		deviceSerice = deviceService;
	}

	private EstimatableIterator<Notification> iteratorNotification() {
		if (broadcast.getLastCallbackToken() != null) {
			logger.info("[{}]Callback from {}", getKey(),
					broadcast.getLastCallbackToken());
			counter.set(broadcast.getCallbacked());
			return notificationService.iteratorByCollapseKey(
					broadcast.getAction(), broadcast.getLastCallbackToken());
		} else {
			logger.info("[{}]Callback from the beginning.", getKey());
			return notificationService.iteratorByCollapseKey(broadcast
					.getAction());
		}
	}

	private void updateStatus() {
		if (broadcast.getStatus() == null
				|| !com.babeeta.appstore.entity.Broadcast.Status.Callbacking
						.equals(broadcast.getStatus())) {
			broadcastDao.updateStatus(broadcast.getId(),
					com.babeeta.appstore.entity.Broadcast.Status.Callbacking);

			broadcast
					.setStatus(com.babeeta.appstore.entity.Broadcast.Status.Callbacking);
		}
	}

	@Override
	public void process() {
		updateStatus();
		EstimatableIterator<Notification> iter = iteratorNotification();
		broadcast.setToBeCallbacked(iter.getTotal());
		broadcastDao.save(broadcast);
		while (iter.hasNext() && !isInterrupted()
				&& !Thread.currentThread().isInterrupted()) {
			updateStatus();
			Notification old = iter.next();
			Notification notification = new Notification()
					.setAction(old.getAction())
					.setCollapseKey(old.getCollapseKey())
					.setDate(new Date())
					.setOwnerId(old.getOwnerId())
					.setStatus(
							com.babeeta.appstore.entity.Notification.Status.deleted)
					.setSubject(old.getSubject());
			notificationService.save(notification);

			// update the lastCallbacking Token of broadcast Entity
			Device device = deviceSerice.getDeviceByToken(notification
					.getOwnerId());
			broadcast.setLastCallbackToken(device.getId())
					.setCallbacked(counter.incrementAndGet());
			broadcastDao.save(broadcast);
		}

		broadcast.setCallbackFinishedDate(new Date());
		broadcastDao.save(broadcast);

		if (!iter.hasNext()) {
			broadcastDao.updateStatus(broadcast.getId(),
					com.babeeta.appstore.entity.Broadcast.Status.Callbacked);
		}

	}
}
