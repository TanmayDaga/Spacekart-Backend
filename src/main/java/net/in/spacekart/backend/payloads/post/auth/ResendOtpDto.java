package net.in.spacekart.backend.payloads.post.auth;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResendOtpDto {

    String orderId;
}
