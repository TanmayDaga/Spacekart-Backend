package net.in.spacekart.backend.payloads.media;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link net.in.spacekart.backend.database.entities.Media}
 */
@Value
public class MediaDto implements Serializable {

    @NotBlank(message = "Empty=images url cannot be stored")
    String url;
}