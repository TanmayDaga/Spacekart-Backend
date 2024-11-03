package net.in.spacekart.backend.services.entityServices;

import net.in.spacekart.backend.database.entities.Review;
import net.in.spacekart.backend.payloads.get.review.ReviewDetailsDtoPrivate;
import net.in.spacekart.backend.payloads.get.review.ReviewDetailsDtoPublic;
import net.in.spacekart.backend.payloads.post.review.ReviewCreateDto;
import net.in.spacekart.backend.repositories.MediaRepository;
import net.in.spacekart.backend.repositories.ReviewRepository;
import net.in.spacekart.backend.services.MediaService;
import net.in.spacekart.backend.services.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final MediaService mediaService;
    private final ReviewRepository reviewRepository;
    private final UtilsService utilsService;
    private MediaRepository mediaRepository;

    @Autowired
    public  ReviewService(MediaService mediaService, ReviewRepository reviewRepository, UtilsService utilsService) {
        this.mediaService = mediaService;
        this.reviewRepository = reviewRepository;
        this.utilsService = utilsService;
        this.mediaRepository = mediaRepository;
    }

    public ReviewDetailsDtoPublic getReviewDetailsPublic(String publicId) {
        return reviewRepository.getReviewDetailsPublic(publicId);
    }

    public ReviewDetailsDtoPrivate getReviewDetailsPrivate(String publicId) {
        return reviewRepository.getReviewDetailsPrivate(publicId);
    }

    public void insertReview(ReviewCreateDto review, String username) {
        Review review1 = new Review();
        review1.setPublicId(utilsService.generateRandomId("review" + username.substring(username.length() - 4)));
        review1.setDescription(review.getDescription());
        review1.setRating(review.getRating());
        Long reviewId = reviewRepository.insertReview(review1, username, review.getSpaceId());
        if (review.getFiles() != null) {
            byte[][] mediaFiles = new byte[review.getFiles().length][];
            for (int i = 0; i < review.getFiles().length; i++) {
                try {
                    mediaFiles[i] = review.getFiles()[i].getBytes();
                } catch (Exception ignored) {
                }
            }
            mediaService.insertMedia(mediaFiles, reviewId, MediaService.MediaType.MULTIPLE_REVIEW_MEDIA);
        }

    }


    public String getUsernameFromReviewId(String reviewId) {
        return reviewRepository.getUserNameFromPublicId(reviewId);
    }

    public Page<String> getAllReviewsBySpace(String spaceId, Pageable pageable) {
        return reviewRepository.getReviews(spaceId, pageable);
    }

    public void deleteReview(String reviewId) {
        reviewRepository.deleteReview(reviewId);
    }
}
