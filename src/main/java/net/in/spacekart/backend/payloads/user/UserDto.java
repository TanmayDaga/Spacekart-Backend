package net.in.spacekart.backend.payloads.user;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import net.in.spacekart.backend.constraints.AgeLimit;
import net.in.spacekart.backend.constraints.FileType;
import net.in.spacekart.backend.database.entities.Address;

import net.in.spacekart.backend.database.entities.Space;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;


@Data
public class UserDto implements Serializable {


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;


    @NotEmpty(message = "First Name cannot be Empty")
    private String firstName;

    private String lastName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String username;


    @Pattern(regexp = "^\\d{10}$", message = "Invalid Phone number. Phone number must be of 10 digits only")
    private String phoneNumber;


    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be of minimum 8 characters.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonProperty("address")
    private Address address;


    @Email(message = "Invalid Email")
    private String emailId;


    @FileType(fileType = "image", nullable = true)
    private MultipartFile profilePicture;

    @AgeLimit(minimumAge = 18)
    private OffsetDateTime birthday;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Space> spaces;


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime updatedAt;


}
