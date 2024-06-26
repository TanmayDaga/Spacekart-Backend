package net.in.spacekart.backend.payloads.user;

public interface GuestUserProjection {
    Long getId();
    String getFirstName();
    String getLastName();
    Media getProfilePicture();
}
