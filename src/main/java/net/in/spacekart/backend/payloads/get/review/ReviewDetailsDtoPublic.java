package net.in.spacekart.backend.payloads.get.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import net.in.spacekart.backend.payloads.post.review.ReviewCreateDto;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for {@link net.in.spacekart.backend.database.entities.Review}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewDetailsDtoPublic implements Serializable {
    String publicId;
    String reviewerUsername;
    Float rating;
    String description;
    List<String> mediaUrls;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
    String reviewerProfilePicture;


    public ReviewDetailsDtoPublic(String publicId, String reviewerUsername, Float rating, String description, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.publicId = publicId;
        this.reviewerUsername = reviewerUsername;
        this.rating = rating;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;


    }

    @Override
    public  String toString() {
        return description+reviewerUsername;
    }
}