package net.in.spacekart.backend.payloads.post.spaceType;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;
import net.in.spacekart.backend.constraints.FileType;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * DTO for {@link net.in.spacekart.backend.database.entities.SpaceType}
 */
@Value
public class SpaceTypeCreateDto implements Serializable {
    @NotBlank(message = "Name=cannot be empty")
    String name;

    MultipartFile image;
}