package net.in.spacekart.backend.payloads.delete.spacetype;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link net.in.spacekart.backend.database.entities.SpaceType}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpaceTypeDeleteDto{

    @NotBlank(message = "Name=cannot be empty")
    String name;

}
