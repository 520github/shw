package com.babeeta.appstore.service;

import com.babeeta.appstore.entity.Device;

public interface DeviceService {

	Device findDeviceByToken(String token);

}
