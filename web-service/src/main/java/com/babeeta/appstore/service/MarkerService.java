package com.babeeta.appstore.service;

import java.util.List;

import com.babeeta.appstore.entity.Marker;
import com.babeeta.appstore.entity.MarkerCounter;
import com.babeeta.appstore.entity.MarkerDomain;

public interface MarkerService {

	/**
	 * 查询某设备某种投票的数量
	 * 
	 * @param domain
	 * @param deviceId
	 * @param value
	 * @return
	 */
	public long countDeviceMarker(String deviceId, String domain, String value);

	/**
	 * 查询某种标记的某个实体的值为value的个数
	 * 
	 * @param markerDomain
	 * @param entityId
	 * @param value
	 * @return
	 */
	public long countDomainMarker(String markerDomain, String entityId,
			String value);

	/**
	 * 查询某设备标记过得app
	 * 
	 * @param device
	 * @param markerDomain
	 * @param value
	 *            标记名
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<Marker> findMarker(String device, String markerDomain,
			String value, int offset, int limit);

	/**
	 * 查找标记
	 * 
	 * @param id
	 * @param domain
	 * @param id2
	 */
	public Marker getMark(String deviceId, String domain, String entityId);

	/**
	 * 得到某个实体的某个标记域的计数器
	 * 
	 * @param domain
	 * @param entityId
	 * @return
	 */
	public MarkerCounter getMarkerCounter(String domain, String entityId);

	/**
	 * 得到某个实体所有的计数器
	 * 
	 * @param entityId
	 * @return
	 */
	public List<MarkerCounter> getMarkerCounters(String entityId);

	/**
	 * 标记
	 * 
	 * @param device
	 * @param markerDomain
	 * @param entityId
	 * @param value
	 * @param counterBy
	 */
	public void mark(String device, String markerDomain, String entityId,
			String value, MarkerDomain.CounterMethod counterBy);

	/**
	 * 删除标记
	 * 
	 * @param device
	 * @param markerDomain
	 * @param entityId
	 * @param value
	 * @param counterBy
	 */
	public void removeMarker(String device, String markerDomain,
			String entityId, MarkerDomain.CounterMethod counterBy);
}
