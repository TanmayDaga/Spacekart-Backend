package net.in.spacekart.backend.services;


public interface MediaService {


    public Long saveMedia(byte[] file, String folderName);

    public void deleteMedia(Long id);

    public void insertMedia(byte[][] files, Long id, MediaType type);




    public enum MediaType {
        SINGLE_USER_PROFILE_PICTURE, MULTIPLE_SPACE_MEDIA, MULTIPLE_SPACE_STREET_VIEW, MULTIPLE_REVIEW_MEDIA, SINGLE_SPACE_TYPE_PICTURE;
    }


}

