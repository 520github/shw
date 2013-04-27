package com.babeeta.appstore.manager.service;

import java.util.Iterator;

import com.babeeta.appstore.dao.impl.EstimatableIterator;
import com.babeeta.appstore.entity.Device;

public interface DeviceService {

	/**
	 * 查询last Device ID之前的设备数
	 * 
	 * @param token
	 * @return
	 */
	public long countByLastDeviceId(String lastDeviceId);

	/**
	 * 查询总设备数
	 * 
	 * @return
	 */
	public long countDevice();

	public Device getDeviceByToken(String token);

	public EstimatableIterator<Device> iteratorByPush();;

	/**
	 * 获取所有可以推送的设备的迭代器
	 * 
	 * @param token
	 * @return
	 */
	public EstimatableIterator<Device> iteratorGreaterThanDeviceId(
			String lastDeviceId);;

	/**
	 * 迭代所有可以发送alert的设备
	 */
	public Iterator<Device> iteratorLessThanOrEqDeviceId(String lastDeviceId);
}
