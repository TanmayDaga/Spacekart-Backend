package net.in.spacekart.backend.repositories;


import jakarta.transaction.Transactional;
import net.in.spacekart.backend.database.entities.Address;
import net.in.spacekart.backend.database.entities.Media;
import net.in.spacekart.backend.database.entities.User;
import net.in.spacekart.backend.database.entities.UserAuthentication;
import net.in.spacekart.backend.payloads.get.user.UserSummaryDtoPublic;
import net.in.spacekart.backend.payloads.put.user.UserPutDto;

import java.util.List;


public interface UserRepository {



    // For authentication
    String getUserNameFromEmailId(String emailId);

    // For authentication
    String getUserNameFromPhoneNumber(String phoneNumber);



    UserAuthentication getUserAuthenticationByUsername(String username);

    void updateUser(UserPutDto userPutDto,String username);

    UserSummaryDtoPublic getUserSummary(String username);

    @Transactional
    Long insertUser(User user);


    boolean checkPhoneNumberExist(String phoneNumber);
    boolean checkEmailExist(String username);
    Long getIdByUsername(String username);



    void deleteUser(String username);

    void deleteProfilePicture(String username);

}

