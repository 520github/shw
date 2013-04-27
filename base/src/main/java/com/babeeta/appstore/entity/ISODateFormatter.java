package com.babeeta.appstore.entity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class ISODateFormatter extends JsonSerializer<Date> {

	@Override
	public void serialize(Date date, JsonGenerator jsonGenerator,
			SerializerProvider serializerProvider)
			throws IOException, JsonProcessingException {
		SimpleDateFormat sdf = new SimpleDateFormat(
				"EEE, d MMM yyyy HH:mm:ss Z");
		jsonGenerator.writeString(sdf.format(date));
	}
}
