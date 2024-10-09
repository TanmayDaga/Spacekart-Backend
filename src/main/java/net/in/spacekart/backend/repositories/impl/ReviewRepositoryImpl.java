package net.in.spacekart.backend.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import net.in.spacekart.backend.database.entities.Media;
import net.in.spacekart.backend.database.entities.Review;
import net.in.spacekart.backend.database.entities.User;
import net.in.spacekart.backend.payloads.get.review.ReviewDetailsDtoPrivate;
import net.in.spacekart.backend.payloads.get.review.ReviewDetailsDtoPublic;
import net.in.spacekart.backend.repositories.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewRepositoryImpl implements ReviewRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public ReviewDetailsDtoPublic getReviewDetailsPublic(String publicId) {
        ReviewDetailsDtoPublic dto = new ReviewDetailsDtoPublic();


           dto =  entityManager.createQuery("select new net.in.spacekart.backend.payloads.get.review.ReviewDetailsDtoPublic(r.publicId, r.reviewer.username, r.rating, r.description, r.createdAt, r.updatedAt)  from Review r where r.publicId = :publicId", ReviewDetailsDtoPublic.class).setParameter("publicId", publicId).getSingleResult();
            List<Media> mediaList = entityManager.createQuery("select r.media from Review r where r.publicId = :publicId", Media.class).setParameter("publicId", publicId).getResultList();
            if(mediaList != null){
                dto.setMediaUrls(mediaList.stream().map(Media::getUrl).toList());
            }
            try{
                String profilePic = entityManager.createQuery("select u.profilePicture.url from User u  where  u.username = :publicId", String.class).setParameter("publicId", dto.getReviewerUsername()).getSingleResult();
                if(profilePic != null){
                    dto.setReviewerProfilePicture(profilePic);
                }
            }catch (Exception ignored){
                dto.setReviewerProfilePicture("");
            }
            return dto;

    }

    @Override
    public String getUserNameFromPublicId(String publicId) {
        return entityManager.createQuery("select r.reviewer.username from Review r where r.publicId = :publicId").setParameter("publicId", publicId).getSingleResult().toString();
    }

    @Override
    public Page<String> getReviews(String space, Pageable pageable) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        Long spaceId = entityManager.createQuery("select s.id from Space s where s.spaceId = :spaceId", Long.class).setParameter("spaceId", space).getSingleResult();
        List<Long> reviewsId = entityManager.createNativeQuery("select sr.reviews_id from space_reviews sr where sr.space_id = ?", String.class).setParameter(1, spaceId).getResultList();
        List<String> reviews = entityManager.createQuery("select r.publicId from Review r where r.id in :reviewId",String.class)
                .setParameter("reviewId", reviewsId)
                .setFirstResult(pageNumber * pageSize)
                .setMaxResults(pageSize)
                .getResultList();


        return new PageImpl<>(reviews, pageable, reviewsId.size());
    }

    @Override
    public ReviewDetailsDtoPrivate getReviewDetailsPrivate(String publicId) {
        ReviewDetailsDtoPrivate dto;
        dto = entityManager.createQuery("select new net.in.spacekart.backend.payloads.get.review.ReviewDetailsDtoPrivate(r.publicId, r.reviewer.username, r.rating, r.description, r.createdAt, r.updatedAt) from Review r where r.publicId = :publicId", ReviewDetailsDtoPrivate.class).setParameter("publicId", publicId).getSingleResult();
        List<Media>  mediaList= entityManager.createQuery("select r.media from  Review  r where r.publicId = :publicId", Media.class).setParameter("publicId", publicId).getResultList();
        if(mediaList != null){
            dto.setMedia(mediaList.stream().map((ele) -> new ReviewDetailsDtoPrivate.MediaDto(ele.getUrl(), ele.getPublicId())).toList());
        }
        return dto;
    }

    @Transactional
    @Override
    public Long insertReview(Review review, String username, String spaceId) {

        Long userId = entityManager.createQuery("select u.id from User u where u.username = :username", Long.class).setParameter("username", username).getSingleResult();
        User user = entityManager.getReference(User.class, userId);
        review.setReviewer(user);
         entityManager.persist(review);
        Long spaceLongId = entityManager.createQuery("select s.id from Space s where s.spaceId = :spaceId", Long.class).setParameter("spaceId", spaceId).getSingleResult();
        entityManager.createNativeQuery("insert into review(reviews_id, space_id) values(?, ?)").setParameter(1, review.getId()).setParameter(2, spaceLongId).executeUpdate();
        return  review.getId();
    }


    @Override
    @Transactional
    public void deleteReview(String reviewId){
        Long id = entityManager.createQuery("select r.id from Review r where r.publicId =   :reviewId", Long.class).setParameter("reviewId", reviewId).getSingleResult();
        Review review = entityManager.getReference(Review.class, id);
        entityManager.remove(review);

    }

}
