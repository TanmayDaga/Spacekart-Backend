package net.in.spacekart.backend;

import net.in.spacekart.backend.services.entityServices.UserService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;


@SpringBootApplication
public class BackendApplication {


    private final DateTimeFormatter dateTimeFormatter;
    private final PasswordEncoder passwordEncoder;
    private final SecureRandom secureRandom;
    private final DateTimeFormatter timeFormatter;



    public BackendApplication() {
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.secureRandom = new SecureRandom();
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss z");

    }


    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }


    @Bean
    public SecureRandom secureRandom() {
        return secureRandom;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return passwordEncoder;
    }

    @Bean
    public DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }


    @Bean
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


        return modelMapper;

    }


    @Bean
    public DateTimeFormatter dateTimeFormatter() {
        return dateTimeFormatter;
    }




}
