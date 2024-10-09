package net.in.spacekart.backend.payloads.allocationTime;

import lombok.Value;
import net.in.spacekart.backend.database.entities.AllocationTime;

import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * DTO for {@link AllocationTime}
 */
@Value
public class AllocationTimeDto implements Serializable {
    OffsetDateTime startTime;
    OffsetDateTime endTime;
    String renterIdUsername;
    String status;
}