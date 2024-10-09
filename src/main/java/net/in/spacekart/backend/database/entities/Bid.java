package net.in.spacekart.backend.database.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Bid(Long id) {
        this.id = id;
    }

    @Column(unique = true)
    private String publicId;


    @ManyToOne
    private Proposal proposal;


    @ManyToOne
    private User bidder;

    @ManyToOne
    private Space space;

    private String description;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER,orphanRemoval = true)
    private Price price;

    @CreationTimestamp
    private OffsetDateTime createAt;

    @UpdateTimestamp
    private OffsetDateTime updateAt;


}
