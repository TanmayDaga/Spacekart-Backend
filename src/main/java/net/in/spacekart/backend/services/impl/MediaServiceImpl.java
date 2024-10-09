package net.in.spacekart.backend.services.impl;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import net.in.spacekart.backend.database.entities.Media;
import net.in.spacekart.backend.repositories.MediaRepository;
import net.in.spacekart.backend.services.CloudinaryService;
import net.in.spacekart.backend.services.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class MediaServiceImpl implements MediaService {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    EntityManager entityManager;


    @Override
    public Long saveMedia(byte[] file, String folderName) {
        Map result = cloudinaryService.uploadFile(file, folderName);

        return mediaRepository.insertMedia(new Media(null, result.get("url").toString(), result.get("public_id").toString(), result.get("asset_id").toString()));
    }

    @Async
    @Transactional
    @Override
    public void deleteMedia(Long id) {

        System.out.println(id);
        String publicId = mediaRepository.getPublicIdById(id);
        cloudinaryService.deleteFile(publicId);
        mediaRepository.deleteById(id);

    }


    @Async
    @Override
    @Transactional
    public void insertMedia(byte[][] files, Long id, MediaType type) {
        if (files == null || files.length == 0) {
            return;
        }
        switch (type) {
            case SINGLE_USER_PROFILE_PICTURE:
                if (files != null && files.length > 0) {
                    Long mediaId = saveMedia(files[0], "profilepicture");
                    mediaRepository.insertUserProfilePicture(mediaId, id);
                }
                break;
            case MULTIPLE_SPACE_MEDIA:
                for (byte[] file : files) {
                    Long mediaId = saveMedia(file, "spacemedia");
                    mediaRepository.insertSpaceMedia(mediaId, id);
                }

                break;
            case MULTIPLE_SPACE_STREET_VIEW:
                for (byte[] file : files) {
                    Long mediaId = saveMedia(file, "spacestreet");
                    mediaRepository.insertSpaceStreetView(mediaId, id);
                }
                break;
            case MULTIPLE_REVIEW_MEDIA:
                for (byte[] file : files) {
                    Long mediaId = saveMedia(file, "reviewmedia");
                    mediaRepository.insertReviewMedia(mediaId, id);
                }

                break;

            case SINGLE_SPACE_TYPE_PICTURE:
                Long mediaId = saveMedia(files[0], "spacetypepicture");
                mediaRepository.insertSpaceTypeMedia(mediaId,id);
                break;


        }
    }




}