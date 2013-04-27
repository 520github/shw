package com.babeeta.appstore.entity;

import java.io.IOException;
import java.util.Set;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class BroadcastStatusSerializer extends JsonSerializer<Broadcast.Status> {
	@Override
	public void serialize(final Broadcast.Status status,
			JsonGenerator jsonGenerator,
			SerializerProvider serializerProvider)
			throws IOException, JsonProcessingException {
		@SuppressWarnings("unused")
		Object o = new Object() {
			public Set<String> getAvailableCommands() {
				return status.getAvailableCommands();
			}

			public String getName() {
				return status.getStatusName();
			}
		};
		jsonGenerator.writeObject(o);
	}
}
