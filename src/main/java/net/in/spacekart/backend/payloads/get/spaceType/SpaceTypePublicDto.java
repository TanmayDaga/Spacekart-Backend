package net.in.spacekart.backend.payloads.get.spaceType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SpaceTypePublicDto {

    private String name;
    private String url;
}
