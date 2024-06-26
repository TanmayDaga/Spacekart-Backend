package net.in.spacekart.backend.payloads;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.in.spacekart.backend.database.entities.Address;
import net.in.spacekart.backend.database.entities.Media;
import net.in.spacekart.backend.database.entities.Space;

import java.sql.Timestamp;
import java.util.List;


@Data
public class UserDto {


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;



    @NotEmpty(message = "First Name cannot be Empty")
    private String firstName;

    private String lastName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String username;


    @Pattern(regexp = "^\\d{10}$", message = "Invalid Phone number. Phone number must be of 10 digits only")
    private String phoneNumber;


    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be of minimum 8 characters.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private Address address;


    @Email(message = "Invalid Email")
    private String emailId;


    private Media profilePicture;

    private String birthday;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Space>  spaces;


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Timestamp createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Timestamp updatedAt;








}
