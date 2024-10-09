package net.in.spacekart.backend.payloads.get.bid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * DTO for {@link net.in.spacekart.backend.database.entities.Bid}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BidDetailDtoPublic implements Serializable {
    String publicId;
    String spaceSpaceId;
    String description;
    String bidderUsername;

    Double priceRatePerMonth;
    Double priceRatePerHour;
    Double priceSecurityDeposit;
    OffsetDateTime createAt;
    OffsetDateTime updateAt;
    String spaceName;
    String spaceType;
    String spaceDimensions;
    Float spaceAverageRating;
    String spaceFeatures;


}

