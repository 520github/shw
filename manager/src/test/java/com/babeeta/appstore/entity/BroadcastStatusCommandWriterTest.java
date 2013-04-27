package com.babeeta.appstore.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.babeeta.appstore.entity.Broadcast.Status;

public class BroadcastStatusCommandWriterTest {

	private final BroadcastStatusCommandWriter broadcastStatusCommandWriter = new BroadcastStatusCommandWriter();

	@Test
	public void testAddCommand() {
		assertTrue(Status.Callbacking.getAvailableCommands().isEmpty());
		broadcastStatusCommandWriter.addCommand(Status.Callbacking, "Stop");
		assertEquals(1, Status.Callbacking.getAvailableCommands().size());
		assertTrue(Status.Callbacking.getAvailableCommands().contains("Stop"));
	}

}
