package net.in.spacekart.backend.services.entityServices;


import jakarta.validation.Validator;
import net.in.spacekart.backend.database.entities.User;
import net.in.spacekart.backend.database.enums.Role;
import net.in.spacekart.backend.payloads.UserDto;
import net.in.spacekart.backend.repositories.UserRepository;
import net.in.spacekart.backend.services.UtilsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserService implements UserDetailsService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    private final ModelMapper modelMapper;

    private final Validator validator;

    private final SecureRandom secureRandom;
    private final UtilsService utilsService;


    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper, Validator validator, SecureRandom secureRandom, PasswordEncoder passwordEncoder, UtilsService utilsService) {

        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.secureRandom = secureRandom;
        this.passwordEncoder = passwordEncoder;
        this.utilsService = utilsService;
    }


    public void saveUser(UserDto user, Role role) {
        User value = modelMapper.map(user, User.class);
        validator.validate(value);


        // Generating user Id;

        String fullName = user.getFirstName() + (StringUtils.hasText(user.getLastName()) ? user.getLastName() : "");
        value.setUsername(String.format("%s-%d%d", fullName, Instant.now().toEpochMilli(), secureRandom.nextLong()));

        value.setPassword(passwordEncoder.encode(user.getPassword()));
        value.setRole(role);
        userRepository.save(value);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }


    public User getUserByEmailIdOrPhoneNumber(String emailIdOrPhoneNumber) {


        if (utilsService.checkEmailIdOrPhoneNumber(emailIdOrPhoneNumber) == UtilsService.EMAILID_PHONENUMBER.EMAIL_ID) {
            return userRepository.findByEmailId(emailIdOrPhoneNumber);
        }
        return userRepository.findByPhoneNumber(emailIdOrPhoneNumber);
    }
    public List<UserDto> getAllUsers() {
       List<UserDto> userDtos = new ArrayList<>();
       for (User user : userRepository.findAll()) {
           userDtos.add(modelMapper.map(user, UserDto.class));
       }
       return userDtos;
     }


}
