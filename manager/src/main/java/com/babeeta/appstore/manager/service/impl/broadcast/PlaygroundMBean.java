package com.babeeta.appstore.manager.service.impl.broadcast;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(objectName = "bean:name=PlaygroundImpl", description = "Playground of jobs.")
public class PlaygroundMBean {
	private final PlaygroundImpl<?> playgroundImpl;

	private PlaygroundMBean(PlaygroundImpl<?> playgroundImpl) {
		super();
		this.playgroundImpl = playgroundImpl;
	}

	@ManagedAttribute()
	public int getActiveWorkers() {
		return playgroundImpl.getActiveWorkers();
	}

	@ManagedAttribute()
	public int getMaxWorkerCount() {
		return playgroundImpl.getMaxWorkerCount();
	}

	@ManagedAttribute()
	public int getQueueLength() {
		return playgroundImpl.getQueueLength();
	}

}
