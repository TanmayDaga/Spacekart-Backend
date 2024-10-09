package net.in.spacekart.backend.payloads.get.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.in.spacekart.backend.constraints.AgeLimit;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * DTO for {@link net.in.spacekart.backend.database.entities.User}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDetailsDtoPrivate implements Serializable {

    @NotBlank(message = "FirstName=cannot be Empty")
    String firstName;
    String lastName;
    String username;
    @Pattern(message = "Invalid Phone number. Phone number must be of 10 digits only", regexp = "^\\d{10}$")
    String phoneNumber;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(message = "Password must be of minimum 8 characters.", min = 8)
    String password;
    String addressPublicId;
    String addressLine;
    String addressLandmark;
    String addressCity;
    String addressState;
    String addressPostalCode;
    @Email(message = "Invalid Email")
    String emailId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    List<String> spaceSpaceIds;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    String profilePictureUrl;
    @AgeLimit(minimumAge = 18, message = "The user must be 18 years or above")
    OffsetDateTime birthday;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
}