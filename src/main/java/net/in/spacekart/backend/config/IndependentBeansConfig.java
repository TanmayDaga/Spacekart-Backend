package net.in.spacekart.backend.config;


import io.github.cdimascio.dotenv.Dotenv;
import org.apache.tika.Tika;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.security.SecureRandom;
import java.time.format.DateTimeFormatter;

@Configuration
public class IndependentBeansConfig {


    private final DateTimeFormatter dateTimeFormatter;
    private final DateTimeFormatter timeFormatter;
    private final SecureRandom secureRandom;
    private final PasswordEncoder passwordEncoder;
    private final Dotenv dotEnv;
    private final Tika tika;

    public IndependentBeansConfig() {
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmssxxxx");
        this.timeFormatter = DateTimeFormatter.ofPattern("HHmmssxxxx");
        this.secureRandom = new SecureRandom();
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.dotEnv = Dotenv.load();
        this.tika = new Tika();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    @Bean
    public DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }

    @Bean
    public DateTimeFormatter getTimeFormatter() {
        return timeFormatter;
    }

    @Bean
    public SecureRandom getSecureRandom() {
        return secureRandom;
    }

    @Bean
    public Dotenv getDotenv() {
        return dotEnv;
    }

    @Bean
    public Tika getTika()  {
        return  tika;
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
