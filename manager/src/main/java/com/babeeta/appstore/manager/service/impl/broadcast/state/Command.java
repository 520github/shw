package com.babeeta.appstore.manager.service.impl.broadcast.state;

import com.babeeta.appstore.entity.Broadcast;
import com.babeeta.appstore.manager.service.impl.broadcast.BroadcastState;

public interface Command {
	public BroadcastState execute(Broadcast broadcast);

	public String getName();
}
