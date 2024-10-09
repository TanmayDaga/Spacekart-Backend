package net.in.spacekart.backend.payloads.put.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.in.spacekart.backend.constraints.AgeLimit;
import net.in.spacekart.backend.constraints.FileType;
import net.in.spacekart.backend.database.entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * DTO for {@link User}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserPutDto implements Serializable {
    @Pattern(message = "Invalid Phone number. Phone number must be of 10 digits only", regexp = "^\\d{10}$")
    String phoneNumber;
    @NotBlank(message = "Address line cannot be empty")
    String addressLine;
    @NotNull
    String addressLandmark;
    @NotBlank(message = "City cannot be exmpty")
    String addressCity;
    @NotBlank(message = "state cannot be empty")
    String addressState;
    @Pattern(message = "Postal code must be 6 digits long", regexp = "^\\d{6}$")
    @NotBlank(message = "postalcode cannot be empty")
    String addressPostalCode;
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid Email")
    String emailId;
    @AgeLimit(minimumAge = 18, message = "Minimum age should be 18")
    OffsetDateTime birthday;
    @FileType(fileType = "image", nullable = true)
    MultipartFile profilePicture;
}