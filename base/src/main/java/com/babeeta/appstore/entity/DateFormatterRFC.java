package com.babeeta.appstore.entity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class DateFormatterRFC extends JsonSerializer<Date> {

	@Override
	public void serialize(Date date, JsonGenerator jsonGenerator,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		jsonGenerator.writeString(sdf.format(date));
	}

}
