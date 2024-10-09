package net.in.spacekart.backend.payloads.get.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * DTO for {@link net.in.spacekart.backend.database.entities.Review}
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ReviewDetailsDtoPrivate implements Serializable {
    String publicId;
    String reviewerUsername;
    Float rating;
    String description;
    List<MediaDto> media;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;

    /**
     * DTO for {@link net.in.spacekart.backend.database.entities.Media}
     */
    @Value
    public static class MediaDto implements Serializable {
        String url;
        String publicId;
    }

    public ReviewDetailsDtoPrivate(String publicId, String reviewerUsername, Float rating, String description, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.publicId = publicId;
        this.reviewerUsername = reviewerUsername;
        this.rating = rating;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}