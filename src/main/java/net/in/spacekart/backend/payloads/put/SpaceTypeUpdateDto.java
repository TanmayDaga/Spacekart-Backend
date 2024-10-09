package net.in.spacekart.backend.payloads.put;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;
import net.in.spacekart.backend.constraints.FileType;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * DTO for {@link net.in.spacekart.backend.database.entities.SpaceType}
 */
@Value
public class SpaceTypeUpdateDto implements Serializable {

    @NotBlank(message = "name=cannot be empty")
    String oldName;

    @NotBlank(message = "name=cannot be empty")
    String newName;

    @FileType(fileType = "image", nullable = true)
    MultipartFile file;


}