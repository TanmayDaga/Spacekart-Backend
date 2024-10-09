package net.in.spacekart.backend.payloads.put.bid;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link net.in.spacekart.backend.database.entities.Bid}
 */
@Value
public class BidPutDto implements Serializable {
    String description;
    Double priceRatePerMonth;
    Double priceRatePerHour;
    Double priceSecurityDeposit;
    String spaceId;
}