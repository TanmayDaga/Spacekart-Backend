package net.in.spacekart.backend.SerializerDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import net.in.spacekart.backend.exceptions.CustomMethodNotArgumentValidException;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class OffsetDateTimeDeserializer extends JsonDeserializer<OffsetDateTime> {


    DateTimeFormatter formatter;

    public OffsetDateTimeDeserializer(DateTimeFormatter formatter) {
        this.formatter = formatter;

    }


    @Override
    public OffsetDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String dateTimeStr = p.getValueAsString();
        try {
            return OffsetDateTime.parse(dateTimeStr, formatter);
        } catch (DateTimeParseException e) {
            throw new CustomMethodNotArgumentValidException(dateTimeStr);
        }
    }
}
