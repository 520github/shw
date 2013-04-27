package com.babeeta.appstore.manager.service.impl.broadcast.state;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.babeeta.appstore.entity.Broadcast;
import com.babeeta.appstore.entity.Broadcast.Status;
import com.babeeta.appstore.manager.service.impl.broadcast.BroadcastState;

@Component
@Scope("singleton")
public class Callbacking extends AbstractBroadcastState {

	class Interrupt implements Command {

		@Override
		public BroadcastState execute(Broadcast broadcast) {
			getBroadcastPlayground().interrupt(broadcast.getId());
			return callbackInterrupted;
		}

		@Override
		public String getName() {
			return "中断";
		}

	}

	@Autowired
	private CallbackInterrupted callbackInterrupted;

	@Override
	public Status getStatus() {
		return Status.Callbacking;
	}

	@PostConstruct
	public void init() {
		this.addCommand(new Interrupt());
	}
}
