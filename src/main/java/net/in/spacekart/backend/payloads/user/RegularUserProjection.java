package net.in.spacekart.backend.payloads.user;

public interface RegularUserProjection {
    Long getId();
    String getFirstName();
    String getLastName();
    String getUsername();
    String getPhoneNumber();
    String getEmailId();
    Address getAddress();
    Media getProfilePicture();
}
