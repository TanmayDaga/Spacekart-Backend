    package net.in.spacekart.backend.payloads.post.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.in.spacekart.backend.constraints.FileType;
import net.in.spacekart.backend.constraints.FileTypeArray;
import net.in.spacekart.backend.database.entities.Review;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * DTO for {@link Review}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewCreateDto implements Serializable {
    Float rating;
    String description;
    String spaceId;
    @FileTypeArray(fileType = "image", nullable = true)
    MultipartFile[] files;
}