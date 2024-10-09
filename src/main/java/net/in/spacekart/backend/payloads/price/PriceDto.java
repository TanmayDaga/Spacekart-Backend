package net.in.spacekart.backend.payloads.price;

import lombok.Value;
import net.in.spacekart.backend.database.entities.Price;

import java.io.Serializable;

/**
 * DTO for {@link Price}
 */
@Value
public class PriceDto implements Serializable {
    Double ratePerMonth;
    Double ratePerHour;
    Double securityDeposit;
}