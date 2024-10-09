package net.in.spacekart.backend.SerializerDeserializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;


public class OffsetTimeSerializer extends JsonSerializer<OffsetTime> {


    DateTimeFormatter formatter;

    public OffsetTimeSerializer(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public void serialize(OffsetTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

        String formattedTime = formatter.format(value);
        gen.writeString(formattedTime);
    }

}