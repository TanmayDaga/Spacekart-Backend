package net.in.spacekart.backend.services.Seeder;

import net.in.spacekart.backend.database.entities.Address;
import net.in.spacekart.backend.database.enums.Role;
import net.in.spacekart.backend.payloads.spaceType.GuestSpaceTypeDto;
import net.in.spacekart.backend.payloads.UserDto;
import net.in.spacekart.backend.services.entityServices.SpaceTypeService;
import net.in.spacekart.backend.services.entityServices.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Seeder {



    @Bean
    CommandLineRunner commandLineRunner(UserService userService, SpaceTypeService spaceTypeService){
        return args -> {
            Address addressDto1 = new Address();
            addressDto1.setLine("123 Main St");
            addressDto1.setLandmark("Near Park");
            addressDto1.setCity("Springfield");
            addressDto1.setState("IL");
            addressDto1.setPostalCode("62701");

            // Create UserDto for the first user
            UserDto userDto1 = new UserDto();
            userDto1.setFirstName("John");
            userDto1.setLastName("Doe");
            userDto1.setUsername("johndoe");
            userDto1.setPhoneNumber("1234567890");
            userDto1.setPassword("securePassword123");
            userDto1.setAddress(addressDto1);
            userDto1.setEmailId("johndoe@example.com");
            userDto1.setBirthday("2000-01-01 00:00:00 +00:00");

            // Create AddressDto for the second user
            Address addressDto2 = new Address();
            addressDto2.setLine("456 Elm St");
            addressDto2.setLandmark("Opposite Mall");
            addressDto2.setCity("Shelbyville");
            addressDto2.setState("IL");
            addressDto2.setPostalCode("62702");

            // Create UserDto for the second user
            UserDto userDto2 = new UserDto();
            userDto2.setFirstName("Jane");
            userDto2.setLastName("Smith");
            userDto2.setUsername("janesmith");
            userDto2.setPhoneNumber("0987654321");
            userDto2.setPassword("securePassword456");
            userDto2.setAddress(addressDto2);
            userDto2.setEmailId("janesmith@example.com");
            userDto2.setBirthday("1990-05-15 00:00:00 +00:00");


            userService.saveUser(userDto1, Role.ROLE_USER);
            userService .saveUser(userDto2, Role.ROLE_ADMIN);

            List<GuestSpaceTypeDto> guestSpaceTypeDtos = new ArrayList<>();

            for (int i = 1; i <= 5; i++) {
                GuestSpaceTypeDto guestSpaceTypeDto = new GuestSpaceTypeDto();
                guestSpaceTypeDto.setName("Entity " + i);
                guestSpaceTypeDtos.add(guestSpaceTypeDto);
            }
            for (GuestSpaceTypeDto guestSpaceTypeDto : guestSpaceTypeDtos) {
                spaceTypeService.save(guestSpaceTypeDto);
            }

        };
    }
}
