package net.in.spacekart.backend.repositories;

import net.in.spacekart.backend.database.entities.Media;
import org.springframework.data.repository.query.Param;

public interface MediaRepository {

    public Long insertMedia(Media media);


    public void deleteById(Long id);

    public String getPublicIdById(@Param("id") Long id);


    public void insertUserProfilePicture(@Param("mediaId") Long mediaId, @Param("id") Long id);

    public void insertSpaceMedia(@Param("mediaId") Long mediaId, @Param("id") Long id);

    public void insertSpaceStreetView(@Param("mediaId") Long mediaId, @Param("id") Long id);


    public void insertReviewMedia(@Param("mediaId") Long mediaId, @Param("id") Long id);


    public void insertSpaceTypeMedia(@Param("mediaId") Long mediaId, @Param("id") Long id);





}