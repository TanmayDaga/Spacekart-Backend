package net.in.spacekart.backend.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.in.spacekart.backend.SerializerDeserializer.OffsetDateTimeDeserializer;
import net.in.spacekart.backend.SerializerDeserializer.OffsetDateTimeSerializer;
import net.in.spacekart.backend.SerializerDeserializer.OffsetTimeDeserializer;
import net.in.spacekart.backend.SerializerDeserializer.OffsetTimeSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class CustomSerializationDeserializationConfig {


    private DateTimeFormatter timeFormatter;

    private DateTimeFormatter dateTimeFormatter;

    @Autowired
    public  CustomSerializationDeserializationConfig(@Qualifier("getTimeFormatter") DateTimeFormatter timeFormatter, @Qualifier("getDateTimeFormatter") DateTimeFormatter dateTimeFormatter) {
        this.timeFormatter = timeFormatter;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Primary
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        SimpleModule module = new SimpleModule();
        module.addSerializer(OffsetTime.class, new OffsetTimeSerializer(timeFormatter));
        module.addDeserializer(OffsetTime.class, new OffsetTimeDeserializer(timeFormatter));
        module.addSerializer(OffsetDateTime.class, new OffsetDateTimeSerializer(dateTimeFormatter));
        module.addDeserializer(OffsetDateTime.class, new OffsetDateTimeDeserializer(dateTimeFormatter));
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(module);

        return objectMapper;
    }
}
