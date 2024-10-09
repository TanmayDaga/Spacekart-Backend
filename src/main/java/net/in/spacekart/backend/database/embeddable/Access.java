package net.in.spacekart.backend.database.embeddable;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetTime;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Access {


    private OffsetTime entryTime;

    private OffsetTime exitTime;

    @Column(nullable = false)
    private String accessType;


}
