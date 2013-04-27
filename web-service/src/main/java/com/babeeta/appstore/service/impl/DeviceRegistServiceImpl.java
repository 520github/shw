package com.babeeta.appstore.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.dao.AppDao;
import com.babeeta.appstore.dao.AppTrackDao;
import com.babeeta.appstore.dao.DeviceDao;
import com.babeeta.appstore.entity.App;
import com.babeeta.appstore.entity.AppTrack;
import com.babeeta.appstore.entity.AppTrack.enumAppSource;
import com.babeeta.appstore.entity.Device;
import com.babeeta.appstore.entity.Device.InstallApp;
import com.babeeta.appstore.service.DeviceRegistService;

@Service("deviceRegistService")
public class DeviceRegistServiceImpl implements DeviceRegistService {

	private DeviceDao deviceDao;
	private AppDao appDao;
	private AppTrackDao appTrackDao;

	@Override
	public Device.InstallApp deleteDeviceInstallApp(String token,
			String packageName) {
		InstallApp app = new InstallApp();
		Device device = deviceDao.findDeviceByToken(token);
		if (device != null) {
			List<Device.InstallApp> apps = device.getApps();
			if (apps == null || apps.size() == 0) {
				return app;
			} else {
				for (int i = 0; i < apps.size(); i++) {
					if (apps.get(i).getPackageName().equals(packageName)) {
						apps.remove(i);
						device.setApps(apps);
						break;
					}
				}
			}
			deviceDao.saveDevice(device);
		}
		return app;
	}

	@Override
	public Device findDeviceByImei(String imei) {
		return deviceDao.findDeviceByImei(imei);
	}

	@Override
	public Device findDeviceByToken(String token) {
		return deviceDao.findDeviceByToken(token);
	}

	@Override
	public List<App> getLasteApps(String token) {
		Device device = deviceDao.findDeviceByToken(token);
		if (device != null && device.getApps() != null) {
			List<Device.InstallApp> list = device.getApps();
			List<String> ids = new ArrayList<String>();
			for (InstallApp temp : list) {
				if (temp.getAppSource() == enumAppSource.coralBay) {
					ids.add(temp.getPackageName());
				}
			}
			if (ids.size() > 0) {
				return appDao.findByIds(ids);

			}
		}
		return new ArrayList<App>();

	}

	@Override
	public void modifyDeviceInstallApp(String token, Device.InstallApp app) {
		Device device = deviceDao.findDeviceByToken(token);
		if (device != null) {
			List<Device.InstallApp> apps = device.getApps();
			if (apps == null || apps.size() == 0) {
				apps.add(app);
				device.setApps(apps);
			} else {
				for (int i = 0; i < apps.size(); i++) {
					if (apps.get(i).getPackageName()
							.equals(app.getPackageName())) {
						apps.set(i, app);
						device.setApps(apps);
						break;
					}
				}
			}
			deviceDao.saveDevice(device);
		}
	}

	@Override
	public void saveAppTrack(String deviceId, AppTrack.enumOperateType type,
			List<Device.InstallApp> apps) {
		Date date = Calendar.getInstance().getTime();
		for (Device.InstallApp app : apps) {
			AppTrack appTrack = new AppTrack();
			appTrack.setId((new ObjectId()).toString());
			appTrack.setDeviceId(deviceId);
			appTrack.setType(type);
			appTrack.setPackageName(app.getPackageName());
			appTrack.setVersionCode(app.getVersionCode());
			appTrack.setOperateDate(date);
			appTrack.setAppSource(app.getAppSource());
			appTrackDao.save(appTrack);
		}
	}

	@Override
	public void saveDevice(Device device) {
		deviceDao.saveDevice(device);
	}

	@Autowired
	public void setAppDao(AppDao appDao) {
		this.appDao = appDao;
	}

	@Autowired
	@Required
	public void setAppTrackDao(AppTrackDao appTrackDao) {
		this.appTrackDao = appTrackDao;
	}

	@Autowired
	@Required
	public void setDeviceDao(DeviceDao deviceDao) {
		this.deviceDao = deviceDao;
	}
}
