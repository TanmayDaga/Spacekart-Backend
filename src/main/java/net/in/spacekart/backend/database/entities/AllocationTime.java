package net.in.spacekart.backend.database.entities;


import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;
import net.in.spacekart.backend.ViewSpaceKart;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class AllocationTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(value = ViewSpaceKart.UserAuthenticated.class)
    private Long id;

    @Column(unique = true)
    private String publicId;

    private OffsetDateTime startTime;
    private OffsetDateTime endTime;

    @ManyToOne
    private User renter;

    private String status;

    @ManyToOne
    private Space space;


    public AllocationTime(Long id) {
        this.id = id;
    }

    public AllocationTime(String publicId, OffsetDateTime startTime, OffsetDateTime endTime, User renter, String status, Space space) {
        this.publicId = publicId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.renter = renter;
        this.status = status;
        this.space = space;
    }
}
