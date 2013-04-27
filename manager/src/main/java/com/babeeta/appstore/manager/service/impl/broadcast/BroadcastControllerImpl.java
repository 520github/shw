package com.babeeta.appstore.manager.service.impl.broadcast;

import java.util.Set;

import com.babeeta.appstore.entity.Broadcast;
import com.babeeta.appstore.manager.service.BroadcastController;
import com.babeeta.appstore.manager.service.impl.broadcast.state.BroadcastStateFactory;

class BroadcastControllerImpl implements BroadcastController {

	private final Broadcast broadcast;

	private BroadcastState broadcastState;

	private final BroadcastStateFactory broadcastStateFactory;

	BroadcastControllerImpl(Broadcast broadcast,
			BroadcastStateFactory broadcastStateFactory) {
		super();
		this.broadcast = broadcast;
		this.broadcastStateFactory = broadcastStateFactory;

		broadcastState = this.broadcastStateFactory
				.getInstanceByStatus(getBroadcast());
	}

	@Override
	public Set<String> getAvailableCommands() {
		return broadcastState.getAvailableCommands();
	}

	@Override
	public Broadcast getBroadcast() {
		return broadcast;
	}

	@Override
	public void run(String command) {
		if (!broadcastState.getAvailableCommands().contains(command)) {
			throw new UnsupportedOperationException(
					"I cannot process command [" + command + "] when I'm in ["
							+ broadcast.getStatus() + "]");
		}
		broadcastState = broadcastState.run(broadcast, command);
	}
}