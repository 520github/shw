package com.babeeta.appstore.manager.service;

import java.util.Date;
import java.util.Map;

public interface PushService {
	public void apnsPush(String deviceToken, int badge);

	public void apnsPush(String deviceToken, int badge, String alert);

	public void apnsPush(String deviceToken, int badge, String alert, String nid);

	public Map<String, Date> getInactiveDevices();

}
