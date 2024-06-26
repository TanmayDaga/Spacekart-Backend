package net.in.spacekart.backend.database.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.sql.Timestamp;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Space space;

    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    private Price price;

    @CreationTimestamp
    private Instant createAt;

    @UpdateTimestamp
    private Instant updateAt;


}
