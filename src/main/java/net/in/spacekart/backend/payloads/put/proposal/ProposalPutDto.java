package net.in.spacekart.backend.payloads.put.proposal;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link net.in.spacekart.backend.database.entities.Proposal}
 */
@Value
public class ProposalPutDto implements Serializable {

    @NotBlank(message = "The description cannot be blank")
    String description;
}