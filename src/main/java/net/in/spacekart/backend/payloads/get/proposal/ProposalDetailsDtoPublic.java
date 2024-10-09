package net.in.spacekart.backend.payloads.get.proposal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import net.in.spacekart.backend.database.entities.Proposal;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * DTO for {@link Proposal}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProposalDetailsDtoPublic implements Serializable {
    String publicId;
    String renterUsername;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
    String description;
    String renterImageUrl;

}