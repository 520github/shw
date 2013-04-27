package com.babeeta.appstore.manager.service.impl.broadcast.state;

import org.springframework.stereotype.Component;

import com.babeeta.appstore.entity.Broadcast.Status;

@Component
public class Callbacked extends AbstractBroadcastState {

	@Override
	public Status getStatus() {
		return Status.Callbacked;
	}

}
