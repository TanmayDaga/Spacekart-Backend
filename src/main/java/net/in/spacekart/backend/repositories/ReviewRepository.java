package net.in.spacekart.backend.repositories;


import net.in.spacekart.backend.database.entities.Review;
import net.in.spacekart.backend.payloads.get.review.ReviewDetailsDtoPrivate;
import net.in.spacekart.backend.payloads.get.review.ReviewDetailsDtoPublic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface ReviewRepository {


    public ReviewDetailsDtoPublic getReviewDetailsPublic(String publicId);


    public ReviewDetailsDtoPrivate getReviewDetailsPrivate(String publicId);


    public Long  insertReview(Review review,String username,String spaceId);

    public String getUserNameFromPublicId(String publicId);

    public Page<String> getReviews(String space, Pageable pageable);

    public void deleteReview(String publicId);
}
