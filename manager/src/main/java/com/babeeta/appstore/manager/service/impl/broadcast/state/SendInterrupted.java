package com.babeeta.appstore.manager.service.impl.broadcast.state;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.babeeta.appstore.entity.Broadcast;
import com.babeeta.appstore.entity.Broadcast.Status;
import com.babeeta.appstore.manager.service.impl.broadcast.BroadcastState;

@Component
public class SendInterrupted extends AbstractBroadcastState {

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

	class Resume implements Command {

		@Override
		public BroadcastState execute(Broadcast broadcast) {
			getBroadcastPlayground().play(
					new SendJob(broadcast, getNotificationService(),
							getDeviceService(), getBroadcastDao(),
							getPushService()), broadcast.getId());
			return sending;
		}

		@Override
		public String getName() {
			return "继续发送";
		}

	}

	@Autowired
	private CallbackPending callbackPending;

	@Autowired
	private Sending sending;

	@Override
	public Status getStatus() {
		return Status.SendInterrupted;
	}

	@PostConstruct
	public void init() {
		this.addCommand(new Resume());
		this.addCommand(new Callback());
	}
}