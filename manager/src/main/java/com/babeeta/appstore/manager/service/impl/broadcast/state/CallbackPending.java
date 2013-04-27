package com.babeeta.appstore.manager.service.impl.broadcast.state;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.babeeta.appstore.entity.Broadcast.Status;

@Component
@Scope("singleton")
public class CallbackPending extends AbstractBroadcastState {

	@Override
	public Status getStatus() {
		return Status.CallbackPending;
	}

}
