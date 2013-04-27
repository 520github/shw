package com.babeeta.appstore.dao;

import java.util.Iterator;
import java.util.List;

import com.babeeta.appstore.dao.impl.EstimatableIterator;
import com.babeeta.appstore.entity.Device;

/**
 * 设备注册DAO
 * 
 * @author papertiger
 * 
 */
public interface DeviceDao {
	/**
	 * 获取device的数量
	 * 
	 * @return
	 */
	public long countDevice();

	/**
	 * 查询last Device ID之前的设备数量
	 * 
	 * @param token
	 * @return
	 */
	public long countDeviceByLastDeviceId(String lastDeviceId);

	public Device findById(String deviceId);

	/**
	 * 根据MAC地址查询设备
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

	/**
	 * 查找所有安装该应用包的设备
	 * 
	 * @param packageName
	 *            应用包名
	 * @return
	 */
	public List<Device> findDevicesByPackageName(String packageName);

	/**
	 * 迭代所有的设备
	 * 
	 */
	public EstimatableIterator<Device> iteratorByPush();

	/**
	 * 迭代所有可以发送alert的设备
	 * 
	 * @param lastDeivce
	 * @return
	 */
	public EstimatableIterator<Device> iteratorGreaterThanLastDeviceId(
			String lastDevice);

	/***
	 * 迭代特定last device id之前的所有设备
	 * 
	 * @param device
	 *            id
	 * @return
	 */
	public Iterator<Device> iteratorLessThanDeviceId(String deviceId);

	/**
	 * 
	 * @param device
	 */
	public void saveDevice(Device device);

}
