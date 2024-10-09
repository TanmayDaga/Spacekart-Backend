package net.in.spacekart.backend.database.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.in.spacekart.backend.constraints.Rating;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String publicId;

    @ManyToOne
    private User reviewer;

    @ManyToOne
    private Space space;

    @Rating
    private Float rating;

    private String description;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Media> media;


    @CreationTimestamp(source = SourceType.DB)
    private OffsetDateTime createdAt;
    @UpdateTimestamp(source = SourceType.DB)
    private OffsetDateTime updatedAt;


    public Review(Long id) {
        this.id = id;
    }
}
