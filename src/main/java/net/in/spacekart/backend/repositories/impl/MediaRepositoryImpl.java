package net.in.spacekart.backend.repositories.impl;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import net.in.spacekart.backend.database.entities.Media;
import net.in.spacekart.backend.repositories.MediaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class MediaRepositoryImpl implements MediaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long insertMedia(Media media) {
        entityManager.persist(media);
        return media.getId();

    }

    @Override
    public void deleteById(Long id) {
        Media media = entityManager.find(Media.class, id);
        if (media != null) {
            entityManager.remove(media);
        }
    }

    @Override
    public String getPublicIdById(Long id) {
        Query query = entityManager.createQuery("SELECT m.publicId FROM Media m WHERE m.id = :id");
        query.setParameter("id", id);
        return (String) query.getSingleResult();
    }

    @Transactional
    @Modifying
    @Override
    public void insertUserProfilePicture(Long mediaId, Long id) {
        entityManager.createNativeQuery(
                        "UPDATE  users set profile_picture_id = ? where id = ?")
                .setParameter(1, mediaId)
                .setParameter(2, id)
                .executeUpdate();
    }

    @Modifying
    @Transactional
    @Override
    public void insertSpaceMedia(Long mediaId, Long id) {

        Query query = entityManager.createNativeQuery(
                "INSERT INTO space_media(media_id, space_id) VALUES(?, ?)");
        query.setParameter(1, mediaId);
        query.setParameter(2, id);
        query.executeUpdate();
    }

    @Modifying
    @Transactional
    @Override
    public void insertSpaceStreetView(Long mediaId, Long id) {
        Query query = entityManager.createNativeQuery(
                "INSERT INTO space_street_view(street_view_id, space_id) VALUES(?, ?)");
        query.setParameter(1, mediaId);
        query.setParameter(2, id);
        query.executeUpdate();
    }

    @Modifying
    @Transactional
    @Override
    public void insertReviewMedia(Long mediaId, Long id) {
        Query query = entityManager.createNativeQuery(
                "INSERT INTO review_media(review_id, space_id) VALUES(?, ?)");
        query.setParameter(1, mediaId);
        query.setParameter(2, id);
        query.executeUpdate();
    }

    @Modifying
    @Transactional
    @Override
    public void insertSpaceTypeMedia(Long mediaId, Long id) {
        Query query = entityManager.createNativeQuery(
                "UPDATE space_type SET image_url_id = ? WHERE id = ?");
        query.setParameter(1, mediaId);
        query.setParameter(2, id);
        query.executeUpdate();
    }


}
