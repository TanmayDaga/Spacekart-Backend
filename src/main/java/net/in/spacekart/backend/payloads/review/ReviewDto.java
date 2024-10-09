package net.in.spacekart.backend.payloads.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import net.in.spacekart.backend.database.entities.Review;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link Review}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto implements Serializable {
    String myUserIdUsername;
    Float rating;
    String comment;
    List<String> photoUrls;
}