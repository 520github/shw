package com.babeeta.appstore.manager.service.impl;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.manager.service.PushService;
import com.babeeta.appstore.manager.service.impl.apns.ApnsServiceFactory;
import com.notnoop.apns.APNS;

@Service("pushService")
@Scope("singleton")
public class PushServiceImpl implements PushService {

	private final static Logger logger = LoggerFactory
			.getLogger(PushServiceImpl.class);
	@Autowired
	private ApnsServiceFactory apnsServiceFactory;

	@Override
	public void apnsPush(String deviceToken, int badge) {
		String payload = APNS.newPayload().badge(badge).build();
		long begin = System.currentTimeMillis();
		apnsServiceFactory.getInstance().push(deviceToken, payload);
		logger.debug("deviceToken[{}],badge[{}] - {}ms", new Object[] {
				deviceToken, badge, System.currentTimeMillis() - begin });
	}

	@Override
	public void apnsPush(String deviceToken, int badge, String alert) {
		String payload = APNS.newPayload().badge(badge).alertBody(alert)
				.sound("default").build();
		long begin = System.currentTimeMillis();
		apnsServiceFactory.getInstance().push(deviceToken, payload);
		logger.debug(
				"deviceToken[{}],badge[{}],alert[{}] - {}ms",
				new Object[] { deviceToken, badge, alert,
						System.currentTimeMillis() - begin });
	}

	@Override
	public void apnsPush(String deviceToken, int badge, String alert, String nid) {
		String payload = APNS.newPayload().badge(badge).alertBody(alert)
				.sound("default").customField("n", nid).build();
		logger.debug("Push: [{}]", deviceToken);
		long begin = System.currentTimeMillis();
		apnsServiceFactory.getInstance().push(deviceToken, payload);
		logger.debug(
				"deviceToken[{}],badge[{}],alert[{}],notification[{}] - {}ms",
				new Object[] { deviceToken, badge, alert, nid,
						System.currentTimeMillis() - begin });

	}

	@Override
	public Map<String, Date> getInactiveDevices() {
		return apnsServiceFactory.getInstance().getInactiveDevices();
	}

}