package net.in.spacekart.backend.payloads;


import lombok.Data;

@Data
public class LoginUserDto {
    private String emailIdOrPhoneNumber;

    private String password;

}