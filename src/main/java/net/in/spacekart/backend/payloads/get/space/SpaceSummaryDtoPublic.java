package net.in.spacekart.backend.payloads.get.space;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link net.in.spacekart.backend.database.entities.Space}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpaceSummaryDtoPublic implements Serializable {
    String name;
    String spaceId;
    double locationLatitude;
    double locationLongitude;
    String typeName;
    Double priceRatePerMonth;
    Double priceRatePerHour;
    Double priceSecurityDeposit;
    String features;
    String dimensions;
    String ownerFirstName;
    String ownerLastName;
    String ownerUsername;
    List<String> photoUrls;
    String description;
    Float averageRating;

    public SpaceSummaryDtoPublic(String name, String spaceId, double locationLatitude, double locationLongitude, String typeName, Double priceRatePerMonth, Double priceRatePerHour, Double priceSecurityDeposit, String features, String dimensions, String ownerFirstName, String ownerLastName, String ownerUsername, String description, Float averageRating) {
        this.name = name;
        this.spaceId = spaceId;
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
        this.typeName = typeName;
        this.priceRatePerMonth = priceRatePerMonth;
        this.priceRatePerHour = priceRatePerHour;
        this.priceSecurityDeposit = priceSecurityDeposit;
        this.features = features;
        this.dimensions = dimensions;
        this.ownerFirstName = ownerFirstName;
        this.ownerLastName = ownerLastName;
        this.ownerUsername = ownerUsername;
        this.description = description;
        this.averageRating = averageRating;
    }
}