package com.babeeta.appstore.manager.notification;

import java.util.Iterator;

import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.dao.AppUpdateNoticeDao;
import com.babeeta.appstore.dao.DeviceDao;
import com.babeeta.appstore.entity.AppUpdateNotice;
import com.babeeta.appstore.entity.Device;

@Service("noticeService")
@Scope("singleton")
public class NoticeService {

	private final Logger LOG = LoggerFactory.getLogger(MessagePush.class);

	private AppUpdateNoticeDao appUpdateNoticeDao;

	private MessagePush messagePush;
	private DeviceDao deviceDao;

	public AppUpdateNoticeDao getAppUpdateNoticeDao() {
		return appUpdateNoticeDao;
	}

	public DeviceDao getDeviceDao() {
		return deviceDao;
	}

	public MessagePush getMessagePush() {
		return messagePush;
	}

	@Autowired
	public void setAppUpdateNoticeDao(AppUpdateNoticeDao appUpdateNoticeDao) {
		this.appUpdateNoticeDao = appUpdateNoticeDao;
	}

	@Autowired
	public void setDeviceDao(DeviceDao deviceDao) {
		this.deviceDao = deviceDao;
	}

	@Autowired
	public void setMessagePush(MessagePush messagePush) {
		this.messagePush = messagePush;
	}

	public void work() throws InterruptedException {

		Iterator<AppUpdateNotice> iterator = appUpdateNoticeDao.find();
		HttpClient httpClient = messagePush.getHttpConnection();
		while (iterator.hasNext()) {
			AppUpdateNotice obj = iterator.next();
			Device device = deviceDao.findById(obj.getDeviceId());
			if (device != null && device.getClientId() != null) {
				if (messagePush.pushMessage(httpClient,
						device.getClientId(), Message.getAppUpdateMessage()) != null) {
					appUpdateNoticeDao.remove(obj);
				}
			}
		}
		httpClient.getConnectionManager().shutdown();
	}

}
