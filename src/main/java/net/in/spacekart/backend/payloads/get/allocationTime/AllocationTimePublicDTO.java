package net.in.spacekart.backend.payloads.get.allocationTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * DTO for {@link net.in.spacekart.backend.database.entities.AllocationTime}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AllocationTimePublicDTO implements Serializable {
    String publicId;
    OffsetDateTime startTime;
    OffsetDateTime endTime;
    String status;
    String spaceSpaceId;
}