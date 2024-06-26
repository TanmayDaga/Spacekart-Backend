package net.in.spacekart.backend.payloads.spaceType;

public interface GuestSpaceTypeProjection {
    UUID getId();
    String getName();
    Media getImageUrl();
}
