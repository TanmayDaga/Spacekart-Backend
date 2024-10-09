package net.in.spacekart.backend.payloads.proposal;

import lombok.Value;
import net.in.spacekart.backend.database.entities.Proposal;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * DTO for {@link Proposal}
 */
@Value
public class ProposalDto implements Serializable {
    String renterUsername;
    List<Long> bidIds;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
}