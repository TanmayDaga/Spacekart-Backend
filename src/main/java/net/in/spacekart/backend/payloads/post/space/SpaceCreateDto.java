package net.in.spacekart.backend.payloads.post.space;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import net.in.spacekart.backend.constraints.FileType;
import net.in.spacekart.backend.constraints.FileTypeArray;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.List;

/**
 * DTO for {@link net.in.spacekart.backend.database.entities.Space}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpaceCreateDto implements Serializable {

    @NotBlank(message = "Name cannot be empty")
    String name;
    @NotNull
    double locationLatitude;
    @NotNull
    double locationLongitude;
    @NotBlank(message = "typeName cannot be empty")
    String typeName;

    Double priceRatePerMonth;
    Double priceRatePerHour;
    Double priceSecurityDeposit;

    @NotBlank(message = "Address Line cannot be empty")
    String addressLine;
    @NotNull
    String addressLandmark;
    @NotBlank(message = "City cannot be empty")
    String addressCity;
    @NotBlank(message = "State cannot be empty")
    String addressState;

    @Pattern(message = "Postal code must be 6 digits long", regexp = "^\\d{6}$")
    @NotBlank(message = "postalcode cannot be empty")
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

    String payment;


    @FileTypeArray(fileType = "image",nullable = true)
    MultipartFile[] photos;
    @FileTypeArray(fileType = "image",nullable = true)
    MultipartFile[] streetView;
    @NotBlank(message = "Description=cannot be Empty")
    String description;
    OffsetTime accessEntryTime;
    OffsetTime accessExitTime;
    String accessAccessType;
    boolean storeOwnItems;
    boolean storeMultipleCustomersItems;
    String separationBetweenItems;
    Integer minimumTimeBeforeRenterArrivesHours;


}