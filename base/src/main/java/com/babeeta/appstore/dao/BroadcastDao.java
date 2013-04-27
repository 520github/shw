package com.babeeta.appstore.dao;

import java.util.List;

import com.babeeta.appstore.entity.Broadcast;
import com.babeeta.appstore.entity.Broadcast.Status;

public interface BroadcastDao {
	public List<Broadcast> findAll(int limit, int offset);

	public Broadcast findById(String id);

	public Broadcast save(Broadcast broadcast);

	/**
	 * 更新广播的状态
	 * 
	 * @param id
	 * @param status
	 */
	public void updateStatus(String id, Status status);
}
