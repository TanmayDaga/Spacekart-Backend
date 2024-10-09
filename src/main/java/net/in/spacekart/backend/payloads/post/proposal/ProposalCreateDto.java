package net.in.spacekart.backend.payloads.post.proposal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link net.in.spacekart.backend.database.entities.Proposal}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProposalCreateDto implements Serializable {
    String description;
}