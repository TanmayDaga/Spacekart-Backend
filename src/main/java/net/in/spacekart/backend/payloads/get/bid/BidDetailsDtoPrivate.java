package net.in.spacekart.backend.payloads.get.bid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import net.in.spacekart.backend.database.entities.Bid;

import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * DTO for {@link Bid}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BidDetailsDtoPrivate implements Serializable {
    String publicId;
    String spaceSpaceId;
    String description;
    String bidderUsername;
    Double priceRatePerMonth;
    Double priceRatePerHour;
    Double priceSecurityDeposit;
    OffsetDateTime createAt;
    OffsetDateTime updateAt;
}