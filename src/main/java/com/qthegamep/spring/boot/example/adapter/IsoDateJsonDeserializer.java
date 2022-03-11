package com.qthegamep.spring.boot.example.adapter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.qthegamep.spring.boot.example.util.Constants;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@JsonComponent
public class IsoDateJsonDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.MONGO_UTC_DATE_FORMAT);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone(Constants.GMT_TIMEZONE));
            JsonNode jsonNode = jsonParser.readValueAsTree();
            String isoDate = jsonNode.get(Constants.JSON_DATE_FIELD_NAME).asText();
            return simpleDateFormat.parse(isoDate);
        } catch (ParseException e) {
            return null;
        }
    }
}
