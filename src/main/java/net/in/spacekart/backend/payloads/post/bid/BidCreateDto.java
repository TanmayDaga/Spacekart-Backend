package net.in.spacekart.backend.payloads.post.bid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link net.in.spacekart.backend.database.entities.Bid}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BidCreateDto implements Serializable {
    String proposalPublicId;
    String spaceSpaceId;
    String description;
    Double priceRatePerMonth;
    Double priceRatePerHour;
    Double priceSecurityDeposit;
}