package net.in.spacekart.backend.payloads.get.space;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link net.in.spacekart.backend.database.entities.SpaceType}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SpaceTypeDto implements Serializable {
    @NotBlank(message = "Name=cannot be empty")
    String name;
}