package net.in.spacekart.backend.payloads.get.space;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import net.in.spacekart.backend.database.entities.Media;
import net.in.spacekart.backend.database.entities.Space;
import net.in.spacekart.backend.payloads.get.media.MediaPrivateDto;

import java.io.Serializable;
import java.time.OffsetTime;
import java.util.List;

/**
 * DTO for {@link Space}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpaceDetailsPrivateDto implements Serializable {
    @NotBlank
    String name;
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
    @Min(message = "minimum RentalPeriod should be at least 1 hour", value = 1)
    @Max(message = "Maximum rental period cannot exceed 1 year", value = 8760)
    Integer minimumRentalPeriodHours;
    @Min(message = "Minimum value cannot precede 1 hour", value = 1)
    @Max(message = "Maximum rental period cannot exceed 1 year", value = 8760)
    Integer maximumRentalPeriodHours;
    List<MediaPrivateDto> media;
    List<MediaPrivateDto> streetView;
    @NotBlank(message = "Description=cannot be Empty")
    String description;
    OffsetTime accessEntryTime;
    OffsetTime accessExitTime;
    String accessAccessType;
    boolean storeOwnItems;
    boolean storeMultipleCustomersItems;
    String separationBetweenItems;
    Integer minimumTimeBeforeRenterArrivesHours;




    public SpaceDetailsPrivateDto(String name, double locationLatitude, double locationLongitude, String typeName, Double priceRatePerMonth, Double priceRatePerHour, Double priceSecurityDeposit, String addressLine, String addressLandmark, String addressCity, String addressState, String addressPostalCode, String features, Integer level, String dimensions, Integer minimumRentalPeriodHours, Integer maximumRentalPeriodHours, String description, OffsetTime accessEntryTime, OffsetTime accessExitTime, String accessAccessType, boolean storeOwnItems, boolean storeMultipleCustomersItems, Integer minimumTimeBeforeRenterArrivesHours, String separationBetweenItems) {
        this.name = name;
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
        this.description = description;
        this.accessEntryTime = accessEntryTime;
        this.accessExitTime = accessExitTime;
        this.accessAccessType = accessAccessType;
        this.storeOwnItems = storeOwnItems;
        this.storeMultipleCustomersItems = storeMultipleCustomersItems;
        this.minimumTimeBeforeRenterArrivesHours = minimumTimeBeforeRenterArrivesHours;
        this.separationBetweenItems = separationBetweenItems;
    }
}
