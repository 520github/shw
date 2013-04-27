package com.babeeta.appstore.manager.service;

import com.babeeta.appstore.entity.Broadcast;
import com.babeeta.appstore.manager.resource.PagedResult;

public interface BroadcastService {
	/**
	 * 创建新的广播
	 * 
	 * @param broadcast
	 * @return
	 */
	public BroadcastController createNewBroadcast(Broadcast broadcast);

	/**
	 * 分页查询所有的广播记录
	 * 
	 * @param limit
	 * @param offset
	 * @return
	 */
	public PagedResult<Broadcast> findAll(int limit, int offset);

	/**
	 * 查询BroadcastController, 根据Broadcast的ID
	 * 
	 * @param id
	 * @return
	 */
	public BroadcastController findBroadcastControllerById(String id);
}
