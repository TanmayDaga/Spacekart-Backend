package net.in.spacekart.backend.database.embeddable;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.FutureOrPresent;


import java.time.OffsetTime;

@Embeddable
public class Access {




    private OffsetTime entryTime;

    private OffsetTime exitTime;

    @Column(nullable = false)
    private String acessType;
}
