package net.in.spacekart.backend.payloads.user;

public interface AdminUserProjection {
    Long getId();
    String getFirstName();
    String getLastName();
    String getUsername();
    String getPhoneNumber();
    String getPassword();
    String getEmailId();
    Address getAddress();
    Media getProfilePicture();
    Role getRole();
    OffsetDateTime getBirthday();
    List<Space> getSpaces();
    Timestamp getCreatedAt();
    Timestamp getUpdatedAt();
}
