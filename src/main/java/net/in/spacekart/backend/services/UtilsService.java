package net.in.spacekart.backend.services;

import net.in.spacekart.backend.database.enums.Role;
import net.in.spacekart.backend.utils.Validation;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UtilsService {


    public enum EMAILID_PHONENUMBER {
        PHONE_NUMBER, EMAIL_ID
    }


    public String getRole(Authentication authentication) {
             if(authentication == null) return Role.ROLE_GUEST.name();
             return authentication.getAuthorities().stream().findFirst().toString();
    }

    public EMAILID_PHONENUMBER checkEmailIdOrPhoneNumber(String username) {
        return  username.contains("@") ? EMAILID_PHONENUMBER.EMAIL_ID : EMAILID_PHONENUMBER.PHONE_NUMBER;
    }
}
