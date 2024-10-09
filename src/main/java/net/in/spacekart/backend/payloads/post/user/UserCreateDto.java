package net.in.spacekart.backend.payloads.post.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import net.in.spacekart.backend.constraints.AgeLimit;
import net.in.spacekart.backend.constraints.FileType;
import net.in.spacekart.backend.database.enums.Role;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * DTO for {@link net.in.spacekart.backend.database.entities.User}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto implements Serializable {
    @NotBlank(message = "FirstName cannot be Empty")
    String firstName;
    @NotNull
    String lastName;
   String phoneNumber;

    @Size(message = "Password must be of minimum 8 characters.", min = 8)
    @NotBlank(message = "Password cannot be Empty")
    String password;

    @NotBlank(message = "Address Line cannot be empty")
    String addressLine;
    @NotNull
    String addressLandmark;
    @NotBlank(message = "City cannot be empty")
    String addressCity;
    @NotBlank(message = "State cannot be empty")
    String addressState;

    @Pattern(message = "Postal code must be 6 digits long", regexp = "^\\d{6}$")
    @NotBlank(message = "postalcode cannot be empty")
    String addressPostalCode;

    @NotBlank(message = "Email cannot be empty")
   String emailId;
    @AgeLimit(minimumAge = 18, message = "Minimum age should be 18")
    OffsetDateTime birthday;

    @FileType(fileType = "image", message = "Invalid File format", nullable = true)
    MultipartFile profilePicture;
}