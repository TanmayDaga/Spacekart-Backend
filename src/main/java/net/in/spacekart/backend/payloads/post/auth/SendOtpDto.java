package net.in.spacekart.backend.payloads.post.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendOtpDto {
    String emailIdOrPhoneNumber;
}