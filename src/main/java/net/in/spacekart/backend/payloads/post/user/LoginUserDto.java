package net.in.spacekart.backend.payloads.post.user;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto implements Serializable {

    @NotBlank(message = "emailIdOrPhoneNumber cannot be blank")
    private String emailIdOrPhoneNumber;

    @Size(message = "Password must be of minimum 8 characters.", min = 8)
    @NotBlank(message = "Password cannot be Empty")
    private String password;

}