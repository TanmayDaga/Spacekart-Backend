package net.in.spacekart.backend.payloads.post.space;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link net.in.spacekart.backend.database.entities.Space}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpaceSummaryDto implements Serializable {
    String name;
    String spaceId;
    String typeName;
    Double priceRatePerMonth;
    Float rating;
    Double latitude;
    Double longitude;
    String description;
    String addressLine;
    String addressLandmark;
    String addressCity;
    String addressState;
    String addressPostalCode;
    String features;
    Integer level;
    String dimensions;
    Integer minimumRentalPeriodHours;
    Integer maximumRentalPeriodHours;
    String photosUrls;


}