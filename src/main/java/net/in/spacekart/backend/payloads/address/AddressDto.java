package net.in.spacekart.backend.payloads.address;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;
import net.in.spacekart.backend.database.entities.Address;

import java.io.Serializable;

/**
 * DTO for {@link Address}
 */
@Value
public class AddressDto implements Serializable {
    @NotBlank(message = "first=line of address cannot be blank")
    String line;
    String landmark;
    @NotBlank(message = "City=cannot be empty")
    String city;
    @NotBlank(message = "State=cannot be empty")
    String state;
    @NotBlank(message = "Postal=Code cannot be Empty")
    String postalCode;
}