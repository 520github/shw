package com.babeeta.appstore.manager.service.impl.broadcast.state;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.babeeta.appstore.dao.BroadcastDao;
import com.babeeta.appstore.entity.Broadcast;
import com.babeeta.appstore.entity.BroadcastStatusCommandWriter;
import com.babeeta.appstore.manager.service.DeviceService;
import com.babeeta.appstore.manager.service.NotificationService;
import com.babeeta.appstore.manager.service.PushService;
import com.babeeta.appstore.manager.service.impl.broadcast.BroadcastState;
import com.babeeta.appstore.manager.service.impl.broadcast.Playground;

public abstract class AbstractBroadcastState implements BroadcastState {

	@Autowired
	private BroadcastDao broadcastDao;

	@Autowired
	private Playground<String> broadcastPlayground;

	@Autowired
	private BroadcastStatusCommandWriter broadcastStatusCommandWriter;

	private final Map<String, Command> commandMap = new HashMap<String, Command>();

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private PushService pushService;

	protected void addCommand(Command command) {
		commandMap.put(command.getName(), command);
		broadcastStatusCommandWriter.addCommand(getStatus(), command.getName());
	}

	protected BroadcastDao getBroadcastDao() {
		return broadcastDao;
	}

	protected Playground<String> getBroadcastPlayground() {
		return broadcastPlayground;
	}

	protected DeviceService getDeviceService() {
		return deviceService;
	}

	protected NotificationService getNotificationService() {
		return notificationService;
	}

	protected BroadcastState process(Broadcast broadcast, Command command) {
		return command.execute(broadcast);
	}

	@Override
	public final Set<String> getAvailableCommands() {
		return commandMap.keySet();
	}

	public PushService getPushService() {
		return pushService;
	}

	@Override
	public final BroadcastState run(Broadcast broadcast, String command) {
		Command cmd = commandMap.get(command);
		if (cmd == null) {
			throw new UnsupportedOperationException();
		}

		BroadcastState next = process(broadcast, cmd);
		broadcast.setStatus(next.getStatus());
		broadcastDao.updateStatus(broadcast.getId(), next.getStatus());
		return next;
	}

}
