package net.in.spacekart.backend.payloads.space;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.sql.Time;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.List;

/**
 * DTO for {@link net.in.spacekart.backend.database.entities.Space}
 */

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SpaceDetailsDto implements Serializable {
    String name;
    String spaceId;
    String typeName;
    Double priceRatePerMonth;
    Double priceRatePerHour;
    Double priceSecurityDeposit;
    @NotBlank(message = "line=cannot be Empty")
    String addressLine;
    String addressLandmark;
    @NotBlank(message = "City=cannot be Empty")
    String addressCity;
    @NotBlank(message = "State=cannot be Empty")
    String addressState;
    @NotBlank(message = "Postal=Code cannot be Empty")
    String addressPostalCode;
    String features;
    @NotBlank(message = "Level=cannot be Empty")
    Integer level;

    @NotBlank(message = "Dimensions=cannot be Empty")
    String dimensions;
    private Integer minimumRentalPeriodHours;

    private Integer maximumRentalPeriodHours;

    List<AllocationTimeDto> allocationTimes;
    String ownerUsername;

    List<String> photoUrls;
    List<String> streetViewUrls;
    @NotBlank(message = "Description=cannot be Empty")
    String description;
    @NotBlank(message = "Entry=Time cannot be Empty")
    OffsetTime accessEntryTime;
    @NotBlank(message = "Exit=Time cannot be Empty")
    OffsetTime accessExitTime;
    String accessType;
    @NotBlank(message = "Please=Check Own Items Option")
    boolean storeOwnItems;
    @NotBlank(message = "Please=Check Multiple Items Option")
    boolean storeMultipleCustomersItems;
    String separationBetweenItems;

    @NotBlank(message = "Minimum=Time before renter arrives cannot be empty")
    Integer minimumTimeBeforeRenterArrivesHours;

    /**
     * DTO for {@link net.in.spacekart.backend.database.entities.AllocationTime}
     */
    @Value
    public static class AllocationTimeDto implements Serializable {
        OffsetDateTime startTime;
        OffsetDateTime endTime;
        String renterIdUsername;
        String status;
    }


}