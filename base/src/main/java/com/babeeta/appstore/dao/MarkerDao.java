package com.babeeta.appstore.dao;

import java.util.List;

import com.babeeta.appstore.entity.Marker;

public interface MarkerDao {
	public long countDeviceMarker(String deviceId, String domain, String value);

	/**
	 * 使用联合主键查询投票
	 * 
	 * @param id
	 * @return
	 */
	public Marker findById(Marker.MarkerId id);

	/**
	 * 查找某设备值为value的标记
	 * 
	 * @param deviceId
	 * @param domain
	 * @param value
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<Marker> findMarker(String deviceId, String domain,
			String value, int offset, int limit);

	/**
	 * 删除某个投票
	 * 
	 * @param id
	 */
	public void removeById(Marker.MarkerId id);

	/**
	 * 存储Marker
	 * 
	 * @param Marker
	 */
	public void save(Marker marker);
}
