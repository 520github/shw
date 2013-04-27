package com.babeeta.appstore.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.babeeta.appstore.entity.Broadcast.Status;

@Component
@Scope("singleton")
public class BroadcastStatusCommandWriter {
	public void addCommand(Status status, String command) {
		status.getAvailableCommandSet().add(command);
	}
}
