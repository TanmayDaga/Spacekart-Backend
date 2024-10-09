package net.in.spacekart.backend.payloads.put.space;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;
import net.in.spacekart.backend.database.entities.Space;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.OffsetTime;
import java.util.List;

/**
 * DTO for {@link Space}
 */
@Value
public class SpacePutPrivateDto implements Serializable {
    @NotBlank(message =  "name field cannot be blank")
    String name;
    @NotBlank(message =  "typeName field cannot be blank")
    String typeName;

    Double priceRatePerMonth;
    Double priceRatePerHour;
    Double priceSecurityDeposit;
    @NotBlank(message =  "addressLine field cannot be blank")
    String addressLine;
    String addressLandmark;
    @NotBlank(message =  "addressCity field cannot be blank")
    String addressCity;
    @NotBlank(message =  "addressState field cannot be blank")
    String addressState;
    @NotBlank(message =  "addressPostalCode field cannot be blank")
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
    @NotBlank(message = "Description=cannot be Empty")
    String description;
    OffsetTime accessEntryTime;
    OffsetTime accessExitTime;
    String accessAccessType;
    boolean storeOwnItems;
    boolean storeMultipleCustomersItems;
    String separationBetweenItems;
    Integer minimumTimeBeforeRenterArrivesHours;


    MultipartFile[] streetViewNewFile;
    MultipartFile[] mediaNewFile;
    String[] streetViewAssetIdsToDelete;
    String[] mediaAssetIdsToDelete;
}