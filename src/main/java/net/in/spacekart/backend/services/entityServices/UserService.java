package net.in.spacekart.backend.services.entityServices;


import net.in.spacekart.backend.database.entities.Address;

import net.in.spacekart.backend.database.entities.User;
import net.in.spacekart.backend.database.enums.Role;
import net.in.spacekart.backend.payloads.get.user.UserSummaryDtoPublic;
import net.in.spacekart.backend.payloads.post.user.UserCreateDto;
import net.in.spacekart.backend.payloads.put.user.UserPutDto;
import net.in.spacekart.backend.repositories.UserRepository;
import net.in.spacekart.backend.services.MediaService;
import net.in.spacekart.backend.services.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.security.SecureRandom;
import java.time.Instant;


@Service
public class UserService implements org.springframework.security.core.userdetails.UserDetailsService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    private final SecureRandom secureRandom;
    private final UtilsService utilsService;
    private final MediaService mediaService;




    @Autowired
    public UserService(UserRepository userRepository, SecureRandom secureRandom, PasswordEncoder passwordEncoder, UtilsService utilsService, MediaService mediaService) {

        this.userRepository = userRepository;

        this.secureRandom = secureRandom;
        this.passwordEncoder = passwordEncoder;
        this.utilsService = utilsService;

        this.mediaService = mediaService;
    }


    public void insertUser(UserCreateDto userCreateDto, Role role) {

        String fullName = userCreateDto.getFirstName() + (StringUtils.hasText(userCreateDto.getLastName()) ? userCreateDto.getLastName() : "");
        String randomNumber = String.format("%d%d", Instant.now().toEpochMilli(), secureRandom.nextInt(10000));
        String userId = String.format("%s-%s", fullName, randomNumber);


        User value = new User(
                null,
                userCreateDto.getFirstName(),
                userCreateDto.getLastName(),
                userId,
                userCreateDto.getPhoneNumber(),
                passwordEncoder.encode(userCreateDto.getPassword()),
                new Address(null, userCreateDto.getAddressLine(), userCreateDto.getAddressLandmark(), userCreateDto.getAddressCity(), userCreateDto.getAddressState(), userCreateDto.getAddressPostalCode()),
                userCreateDto.getEmailId(),
                role,
                null,
                userCreateDto.getBirthday(),
                null,
                null
        );


        // Generating user Id
        Long id = userRepository.insertUser(value);

        try {
            byte[][] array = {userCreateDto.getProfilePicture().getBytes()};
            mediaService.insertMedia(array, id, MediaService.MediaType.SINGLE_USER_PROFILE_PICTURE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void updateUser(UserPutDto userPutDto, String username) {

        if(userPutDto.getProfilePicture() != null){
            userRepository.deleteProfilePicture(username);
            Long userId =  userRepository.getIdByUsername(username);

            try {
                    byte[][] array = {userPutDto.getProfilePicture().getBytes()};
                    mediaService.insertMedia(array,userId, MediaService.MediaType.SINGLE_USER_PROFILE_PICTURE);
                }catch (Exception ignored){}


        }

        userRepository.updateUser(userPutDto,username);
    }


    public void deleteUser(String username) {
        userRepository.deleteUser(username);
    }


    public String getUserByEmailIdOrPhoneNumber(String emailIdOrPhoneNumber) {


        if (utilsService.checkEmailIdOrPhoneNumber(emailIdOrPhoneNumber) == UtilsService.EMAILID_PHONENUMBER.EMAIL_ID) {
            return userRepository.getUserNameFromEmailId(emailIdOrPhoneNumber);
        }
        return userRepository.getUserNameFromPhoneNumber(emailIdOrPhoneNumber);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getUserAuthenticationByUsername(username);
    }


    public UserSummaryDtoPublic getUserSummaryByUsername(String username) {
        return userRepository.getUserSummary(username);
    }


    public boolean checkPhoneNumberExists(String phoneNumber) {
        return userRepository.checkPhoneNumberExist(phoneNumber);
    }
    public boolean checkEmailIdExists(String emailId) {
        return userRepository.checkEmailExist(emailId);
    }






}
