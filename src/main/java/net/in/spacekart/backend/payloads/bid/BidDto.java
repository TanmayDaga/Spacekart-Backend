package net.in.spacekart.backend.payloads.bid;

import lombok.Value;

import java.io.Serializable;
import java.time.Instant;
import java.time.OffsetDateTime;

/**
 * DTO for {@link net.in.spacekart.backend.database.entities.Bid}
 */
@Value
public class BidDto implements Serializable {
    String spaceSpaceId;
    String description;
    Double priceRatePerMonth;
    Double priceRatePerHour;
    Double priceSecurityDeposit;
    OffsetDateTime createAt;
    OffsetDateTime updateAt;
}