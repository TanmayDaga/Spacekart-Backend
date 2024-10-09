package net.in.spacekart.backend.database.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Proposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String publicId;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    private User renter;

    @OneToMany( mappedBy = "proposal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bid> bids;


    @CreationTimestamp(source = SourceType.DB)
    private OffsetDateTime createdAt;
    @UpdateTimestamp(source = SourceType.DB)
    private OffsetDateTime updatedAt;


    public Proposal(Long id) {
        this.id = id;
    }
}
