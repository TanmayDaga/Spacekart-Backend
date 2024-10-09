package net.in.spacekart.backend.payloads.get.allocationTime;

import lombok.Value;

import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * DTO for {@link net.in.spacekart.backend.database.entities.AllocationTime}
 */
@Value
public class AllocationTimePrivateDto implements Serializable {
    String publicId;
    OffsetDateTime startTime;
    OffsetDateTime endTime;
    String renterUsername;
    String status;
    String spaceSpaceId;
}