package com.babeeta.appstore.manager.service.impl.broadcast.state;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.babeeta.appstore.entity.Broadcast;
import com.babeeta.appstore.entity.Broadcast.Status;
import com.babeeta.appstore.manager.service.impl.broadcast.BroadcastState;

/**
 * 初始状态，很快就能进入Pending了
 * 
 * @author leon
 * 
 */
@Component
public class InEden extends AbstractBroadcastState {

	class Bootstrap implements Command {

		@Override
		public BroadcastState execute(Broadcast broadcast) {
			getBroadcastPlayground().play(
					new SendJob(broadcast, getNotificationService(),
							getDeviceService(), getBroadcastDao(),
							getPushService()), broadcast.getId());
			return sendPending;
		}

		@Override
		public String getName() {
			return "启动";
		}

	}

	@Autowired
	private SendPending sendPending;

	@Override
	public Status getStatus() {
		return Status.InEden;
	}

	@PostConstruct
	public void init() {
		addCommand(new Bootstrap());
	}

}
