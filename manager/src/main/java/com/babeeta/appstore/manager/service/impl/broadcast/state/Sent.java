package com.babeeta.appstore.manager.service.impl.broadcast.state;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.babeeta.appstore.entity.Broadcast;
import com.babeeta.appstore.entity.Broadcast.Status;
import com.babeeta.appstore.manager.service.impl.broadcast.BroadcastState;

@Component
public class Sent extends AbstractBroadcastState {

	class Callback implements Command {

		@Override
		public BroadcastState execute(Broadcast broadcast) {
			getBroadcastPlayground().play(
					new CallbackJob(broadcast, getNotificationService(),
							getBroadcastDao(), getDeviceService()),
					broadcast.getId());
			return callbackPending;
		}

		@Override
		public String getName() {
			return "召回";
		}

	}

	@Autowired
	private CallbackPending callbackPending;

	@Override
	public Status getStatus() {
		return Status.Sent;
	}

	@PostConstruct
	public void init() {
		addCommand(new Callback());
	}
}
