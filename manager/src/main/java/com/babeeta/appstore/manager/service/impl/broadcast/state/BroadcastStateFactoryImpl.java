package com.babeeta.appstore.manager.service.impl.broadcast.state;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.babeeta.appstore.entity.Broadcast;
import com.babeeta.appstore.manager.service.impl.broadcast.BroadcastState;

@Component
@Scope("singleton")
public class BroadcastStateFactoryImpl implements BroadcastStateFactory {

	private final Map<String, BroadcastState> stateMap;

	public BroadcastStateFactoryImpl() {
		stateMap = null;

	}

	public BroadcastStateFactoryImpl(Map<String, BroadcastState> stateMap) {
		super();
		this.stateMap = stateMap;
	}

	@Override
	public BroadcastState getInstanceByStatus(Broadcast broadcast) {
		return stateMap.get(broadcast.getStatus().name());
	}
}
