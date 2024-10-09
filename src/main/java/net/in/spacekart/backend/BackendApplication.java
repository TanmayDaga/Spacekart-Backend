package net.in.spacekart.backend;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;


@SpringBootApplication
public class BackendApplication {


    @Qualifier("getDateTimeFormatter")
    @Autowired
    private DateTimeFormatter dateTimeFormatter;
    @Qualifier("getTimeFormatter")
    @Autowired
    private DateTimeFormatter timeFormatter;


    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }


    @Bean
    @Primary
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();


        Converter<String, OffsetDateTime> StringToOffsetDateTimeConverter = mappingContext -> OffsetDateTime.parse(mappingContext.getSource(), dateTimeFormatter);
        Converter<OffsetDateTime, String> OffsetDateTimeToStringConverter = mappingContext -> mappingContext.getSource().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        Converter<String, OffsetTime> StringToOffsetTimeConverter = mappingContext -> OffsetTime.parse(mappingContext.getSource(), timeFormatter);
        Converter<OffsetTime, String> OffsetTimeToStringConverter = mappingContext -> mappingContext.getSource().format(timeFormatter);


        modelMapper.createTypeMap(OffsetDateTime.class, String.class).setConverter(OffsetDateTimeToStringConverter);
        modelMapper.createTypeMap(String.class, OffsetDateTime.class).setConverter(StringToOffsetDateTimeConverter);
        modelMapper.createTypeMap(OffsetTime.class, String.class).setConverter(OffsetTimeToStringConverter);
        modelMapper.createTypeMap(String.class, OffsetTime.class).setConverter(StringToOffsetTimeConverter);

        modelMapper.getConfiguration().setAmbiguityIgnored(false);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        System.out.println(modelMapper.getConfiguration().setSkipNullEnabled(false).getConverters().toString());

        return modelMapper;
    }


}
