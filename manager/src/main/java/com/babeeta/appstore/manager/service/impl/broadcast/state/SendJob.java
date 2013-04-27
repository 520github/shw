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
import com.babeeta.appstore.entity.Notification.Status;
import com.babeeta.appstore.manager.service.DeviceService;
import com.babeeta.appstore.manager.service.NotificationService;
import com.babeeta.appstore.manager.service.PushService;

public class SendJob extends AbstractJob<String> {
	private static final Logger logger = LoggerFactory.getLogger(SendJob.class);

	private final Broadcast broadcast;
	private final BroadcastDao broadcastDao;
	private final AtomicInteger counter = new AtomicInteger();

	private final DeviceService deviceService;
	private final NotificationService notificationService;
	private final PushService pushService;

	public SendJob(Broadcast broadcast,
			NotificationService notificationService,
			DeviceService deviceService, BroadcastDao broadcastDao,
			PushService pushService) {
		super(broadcast.getId());
		this.broadcast = broadcast;
		this.notificationService = notificationService;
		this.deviceService = deviceService;
		this.broadcastDao = broadcastDao;
		this.pushService = pushService;
	}

	private EstimatableIterator<Device> iteratorDevice() {
		if (broadcast.getLastSendingToken() != null) {
			logger.info("[{}]Send from [{}]", getKey(),
					broadcast.getLastSendingToken());
			counter.set(broadcast.getSent());
			return deviceService.iteratorGreaterThanDeviceId(broadcast
					.getLastSendingToken());
		} else {
			logger.info("[{}]Send from the beginning.", getKey());
			return deviceService.iteratorByPush();
		}
	}

	private void updateStatus() {
		if (broadcast.getStatus() == null
				|| !com.babeeta.appstore.entity.Broadcast.Status.Sending
						.equals(broadcast.getStatus())) {
			broadcastDao.updateStatus(broadcast.getId(),
					com.babeeta.appstore.entity.Broadcast.Status.Sending);
			broadcast
					.setStatus(com.babeeta.appstore.entity.Broadcast.Status.Sending);

		}
	}

	@Override
	public void process() {
		updateStatus();
		EstimatableIterator<Device> iter = iteratorDevice();
		broadcast.setToBeSent(iter.getTotal());
		broadcastDao.save(broadcast);
		while (iter.hasNext() && !isInterrupted()
				&& !Thread.currentThread().isInterrupted()) {
			Device device = iter.next();
			Notification notification = new Notification()
					.setAction(broadcast.getAction())
					.setCollapseKey(broadcast.getAction()).setDate(new Date())
					.setOwnerId(device.getToken()).setStatus(Status.normal)
					.setSubject(broadcast.getSubject());
			notificationService.save(notification);
			broadcast.setLastSendingToken(device.getId());
			broadcastDao.save(broadcast);
			// if (StringUtils.isNotBlank(device.getDeviceToken())
			// && device.isPushAlert()) {
			// logger.debug("[{}] {}", broadcast.getId(), device.getToken());
			// // update the lastSendingToken of broadcast Entity
			// int badge = (int) notificationService.countBadge(device);
			// pushService.apnsPush(device.getDeviceToken(), badge,
			// broadcast.getSubject(), notification.getId());
			// }
			broadcast.setSent(counter.incrementAndGet());
		}

		broadcast.setSendFinishedDate(new Date());
		broadcastDao.save(broadcast);

		if (!iter.hasNext()) {
			// update broadcast status to sent after send-job completed
			broadcastDao.updateStatus(broadcast.getId(),
					com.babeeta.appstore.entity.Broadcast.Status.Sent);
		} else {
			broadcastDao
					.updateStatus(
							broadcast.getId(),
							com.babeeta.appstore.entity.Broadcast.Status.SendInterrupted);
		}

	}
}
