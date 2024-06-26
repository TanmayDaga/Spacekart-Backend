package net.in.spacekart.backend.database.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class AllocationTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;

    @OneToOne(cascade = CascadeType.PERSIST)
    private User renterId;

    private String status;

}
