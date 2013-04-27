package com.babeeta.appstore.manager.service.impl.broadcast;

import com.babeeta.appstore.manager.service.BroadcastController;

/**
 * BroadcastController的工厂
 * 
 * @author leon
 * 
 */
public interface BroadcastControllerFactory {
	public BroadcastController getInstance(String id);
}
