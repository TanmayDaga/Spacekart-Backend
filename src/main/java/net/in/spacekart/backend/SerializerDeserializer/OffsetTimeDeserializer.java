package net.in.spacekart.backend.SerializerDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import net.in.spacekart.backend.exceptions.CustomMethodNotArgumentValidException;

import java.io.IOException;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class OffsetTimeDeserializer extends JsonDeserializer<OffsetTime> {

    DateTimeFormatter formatter;

    public OffsetTimeDeserializer(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }


    @Override
    public OffsetTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String timeStr = p.getValueAsString();
        try {
            return OffsetTime.parse(p.getValueAsString(), formatter);
        } catch (DateTimeParseException e) {
            throw new CustomMethodNotArgumentValidException(timeStr);
        }
    }
}