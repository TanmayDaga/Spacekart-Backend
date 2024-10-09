package net.in.spacekart.backend.payloads.put.allocationTime;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllocationTimePut {
    String status;
    OffsetDateTime startTime;
    OffsetDateTime endTime;
}
