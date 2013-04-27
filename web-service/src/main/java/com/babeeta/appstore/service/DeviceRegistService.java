package com.babeeta.appstore.service;

import java.util.List;

import com.babeeta.appstore.entity.App;
import com.babeeta.appstore.entity.AppTrack;
import com.babeeta.appstore.entity.Device;

public interface DeviceRegistService {

	/**
	 * 修改已经上传的apps列表
	 * 
	 * @param app
	 */
	public Device.InstallApp deleteDeviceInstallApp(String token,
			String packageName);

	/**
	 * 根据IMEI查询设备
	 * 
	 * @param mac
	 * @return
	 */
	public Device findDeviceByImei(String imei);

	/**
	 * 根据token 查询设备
	 * 
	 * @param token
	 * @return
	 */
	public Device findDeviceByToken(String token);

	public List<App> getLasteApps(String token);

	/**
	 * 修改已经上传的apps列表,如果没有则添加
	 * 
	 * @param app
	 */
	public void modifyDeviceInstallApp(String token, Device.InstallApp app);

	/**
	 * 存储APP日志跟踪
	 * 
	 * @param token
	 *            客户端设备编号
	 * @param type
	 *            操作类型
	 * @param app
	 *            安装应用
	 */
	public void saveAppTrack(String token, AppTrack.enumOperateType type,
			List<Device.InstallApp> app);

	/**
	 * 存储设备
	 * 
	 * @param device
	 */
	public void saveDevice(Device device);
}
