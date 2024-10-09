package net.in.spacekart.backend.payloads.get.space;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link net.in.spacekart.backend.database.entities.Space}
 */
@Value
public class SpaceListDto implements Serializable {
    String name;
    String spaceId;
}