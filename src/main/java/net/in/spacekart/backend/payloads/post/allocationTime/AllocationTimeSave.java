package net.in.spacekart.backend.payloads.post.allocationTime;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllocationTimeSave {
    String SpaceId;
    OffsetDateTime startTime;
    OffsetDateTime endTime;
    String status;
}
