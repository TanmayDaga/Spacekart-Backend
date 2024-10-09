package net.in.spacekart.backend.services;

import net.in.spacekart.backend.database.enums.Role;
import net.in.spacekart.backend.repositories.UserRepository;
import net.in.spacekart.backend.repositories.UtilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;

@Service
public class UtilsService {


    @Autowired
    SecureRandom secureRandom;

    @Autowired
    UtilRepository utilRepository;

    public Role getRole(Authentication authentication) {
        if (authentication == null) return Role.ROLE_GUEST;
        return Role.valueOf(authentication.getAuthorities().stream().findFirst().toString());
    }

    public EMAILID_PHONENUMBER checkEmailIdOrPhoneNumber(String username) {
        return username.contains("@") ? EMAILID_PHONENUMBER.EMAIL_ID : EMAILID_PHONENUMBER.PHONE_NUMBER;
    }

    public String generateRandomId(String initialString) {
        return String.format("%s-%d%d", initialString, Instant.now().toEpochMilli(), secureRandom.nextInt(10000));
    }

    public String getUsernameSpacePublicId(String publicSpaceId) {
        return utilRepository.getUsernameSpacePublicId(publicSpaceId);
    }

    public String getUsernameReviewPublicId(String publicReviewId) {
        return utilRepository.getUsernameReviewPublicId(publicReviewId);
    }

    public String getUsernameBidPublicId(String publicBidId) {
        return utilRepository.getUsernameBidPublicId(publicBidId);
    }

    public String getUsernameProposalPublicId(String proposalId) {
        return utilRepository.getUsernameProposalPublicId(proposalId);
    }

    public String getUsernameAllocationTimePublicId(String allocationtimeId) {
        return  utilRepository.getUsernameAllocationTimePublicId(allocationtimeId);
    }

    public enum EMAILID_PHONENUMBER {
        PHONE_NUMBER, EMAIL_ID
    }


}
