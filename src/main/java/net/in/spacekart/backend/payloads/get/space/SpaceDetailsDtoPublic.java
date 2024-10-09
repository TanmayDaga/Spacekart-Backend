package net.in.spacekart.backend.payloads.get.space;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.List;

/**
 * DTO for {@link net.in.spacekart.backend.database.entities.Space}
 */
@Data
@NoArgsConstructor
public class SpaceDetailsDtoPublic implements Serializable {
    String name;
    String spaceId;
    double locationLatitude;
    double locationLongitude;
    String typeName;
    Double priceRatePerMonth;
    Double priceRatePerHour;
    Double priceSecurityDeposit;
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
    String payment;
    String ownerFirstName;
    String ownerLastName;
    String ownerUsername;
    String ownerProfilePictureUrl;
    List<String> photoUrls;
    List<String> streetViewUrls;
    String description;
    OffsetTime accessEntryTime;
    OffsetTime accessExitTime;
    String accessAccessType;
    boolean storeOwnItems;
    boolean storeMultipleCustomersItems;
    String separationBetweenItems;
    Integer minimumTimeBeforeRenterArrivesHours;
    Float averageRating;

    public SpaceDetailsDtoPublic(
            String name, String spaceId, double locationLatitude, double locationLongitude, String typeName,
            Double priceRatePerMonth, Double priceRatePerHour, Double priceSecurityDeposit, String addressLine,
            String addressLandmark, String addressCity, String addressState, String addressPostalCode, String features,
            Integer level, String dimensions, Integer minimumRentalPeriodHours, Integer maximumRentalPeriodHours,
            String payment, String ownerFirstName,
            String ownerLastName, String ownerUsername, String description, OffsetTime accessEntryTime,
            OffsetTime accessExitTime, String accessAccessType, boolean storeOwnItems, boolean storeMultipleCustomersItems,
            String separationBetweenItems, Integer minimumTimeBeforeRenterArrivesHours, Float averageRating) {
        this.name = name;
        this.spaceId = spaceId;
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
        this.typeName = typeName;
        this.priceRatePerMonth = priceRatePerMonth;
        this.priceRatePerHour = priceRatePerHour;
        this.priceSecurityDeposit = priceSecurityDeposit;
        this.addressLine = addressLine;
        this.addressLandmark = addressLandmark;
        this.addressCity = addressCity;
        this.addressState = addressState;
        this.addressPostalCode = addressPostalCode;
        this.features = features;
        this.level = level;
        this.dimensions = dimensions;
        this.minimumRentalPeriodHours = minimumRentalPeriodHours;
        this.maximumRentalPeriodHours = maximumRentalPeriodHours;

        this.payment = payment;
        this.ownerFirstName = ownerFirstName;
        this.ownerLastName = ownerLastName;
        this.ownerUsername = ownerUsername;


        this.description = description;
        this.accessEntryTime = accessEntryTime;
        this.accessExitTime = accessExitTime;
        this.accessAccessType = accessAccessType;
        this.storeOwnItems = storeOwnItems;
        this.storeMultipleCustomersItems = storeMultipleCustomersItems;
        this.separationBetweenItems = separationBetweenItems;
        this.minimumTimeBeforeRenterArrivesHours = minimumTimeBeforeRenterArrivesHours;
        this.averageRating = averageRating;
    }

    /**
     * DTO for {@link net.in.spacekart.backend.database.entities.AllocationTime}
     */
    @Value
    public static class AllocationTimeDto implements Serializable {
        OffsetDateTime startTime;
        OffsetDateTime endTime;
        String status;
    }
}