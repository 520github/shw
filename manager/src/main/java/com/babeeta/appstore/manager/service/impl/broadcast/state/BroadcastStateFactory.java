package com.babeeta.appstore.manager.service.impl.broadcast.state;

import com.babeeta.appstore.entity.Broadcast;
import com.babeeta.appstore.manager.service.impl.broadcast.BroadcastState;

public interface BroadcastStateFactory {
	/**
	 * 获取对应Broadcast状态的状态对象
	 * 
	 * @param status
	 * @return
	 */
	public BroadcastState getInstanceByStatus(Broadcast broadcast);
}
