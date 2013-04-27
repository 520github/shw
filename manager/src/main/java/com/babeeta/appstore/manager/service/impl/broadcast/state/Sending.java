package com.babeeta.appstore.manager.service.impl.broadcast.state;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.babeeta.appstore.entity.Broadcast;
import com.babeeta.appstore.entity.Broadcast.Status;
import com.babeeta.appstore.manager.service.impl.broadcast.BroadcastState;

/**
 * 正在发送状态
 * 
 * @author leon
 * 
 */
@Component
public class Sending extends AbstractBroadcastState {
	class Callback extends Interrupt {

		@Override
		public BroadcastState execute(final Broadcast broadcast) {
			super.execute(broadcast);
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

	class Interrupt implements Command {

		@Override
		public BroadcastState execute(Broadcast broadcast) {
			getBroadcastPlayground().interrupt(broadcast.getId());
			return sendInterrupted;
		}

		@Override
		public String getName() {
			return "中断";
		}

	}

	@Autowired
	private CallbackPending callbackPending;
	@Autowired
	private SendInterrupted sendInterrupted;

	@Override
	public Status getStatus() {
		return Status.Sending;
	}

	@PostConstruct
	public void init() {
		addCommand(new Callback());
		addCommand(new Interrupt());
	}
}
