package net.in.spacekart.backend.services.Seeder;

import net.in.spacekart.backend.services.entityServices.SpaceTypeService;
import net.in.spacekart.backend.services.entityServices.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class Seeder {


    @Bean
    CommandLineRunner commandLineRunner(UserService userService, SpaceTypeService spaceTypeService) {
        return args -> {};


    }
}
