package net.in.spacekart.backend.payloads.post.space;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpaceDetailsDto implements Serializable {

    String spaceId;
    String name;
    String description;
    Double latitude;
    Double longitude;
    String typeName;
    Double ratePerMonth;
    Double ratePerYear;
    Double securityDeposit;




}
