package com.qthegamep.application.adapter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.qthegamep.application.util.Constants;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@JsonComponent
public class IsoDateJsonSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.MONGO_UTC_DATE_FORMAT);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(Constants.GMT_TIMEZONE));
        String isoDate = simpleDateFormat.format(date);
        jsonGenerator.writeStartObject(Constants.JSON_DATE_FIELD_NAME);
        jsonGenerator.writeObjectField(Constants.JSON_DATE_FIELD_NAME, isoDate);
        jsonGenerator.writeEndObject();
    }
}
