package net.in.spacekart.backend.payloads.get.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MediaPrivateDto {

    String url;
    String assetId;
}
