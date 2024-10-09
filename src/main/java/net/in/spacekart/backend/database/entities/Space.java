package net.in.spacekart.backend.database.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.in.spacekart.backend.database.embeddable.Access;
import net.in.spacekart.backend.database.embeddable.Location;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Space {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private String spaceId;

    private Location location;


    @ManyToOne
    private SpaceType type;

    @OneToOne(cascade = CascadeType.ALL)
    private Price price;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    private String features;

    @Column(nullable = false)
    private Integer level;

    @Column(nullable = false)
    private String dimensions;

    @Column(nullable = false)
    @Min(value = 1, message = "minimum RentalPeriod should be at least 1 hour")
    @Max(value = 24 * 365, message = "Maximum rental period cannot exceed 1 year")
    private Integer minimumRentalPeriodHours;

    @Column(nullable = false)
    @Min(value = 1, message = "Minimum value cannot precede 1 hour")
    @Max(value = 24 * 365, message = "Maximum rental period cannot exceed 1 year")
    private Integer maximumRentalPeriodHours;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "space")
    private List<Review> reviews;

    @OneToMany( mappedBy = "space",cascade = CascadeType.ALL,fetch = FetchType.LAZY ,orphanRemoval = true)
    private List<AllocationTime> allocationTimes;

    private String payment;


    @ManyToOne
    private User owner;


    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY ,orphanRemoval = true)
    private List<Media> media;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY ,orphanRemoval = true )
    private List<Media> streetView;

    @OneToMany(mappedBy = "space", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Bid> bids;

    @Column(nullable = false)
    @NotBlank(message = "Description=cannot be Empty")
    private String description;

    @Embedded
    private Access access;

    private boolean storeOwnItems;

    private boolean storeMultipleCustomersItems;

    private String separationBetweenItems;

    private Integer minimumTimeBeforeRenterArrivesHours;

    @Column(updatable = false, insertable = false)
    private Integer reviewsCount;

    private Float averageRating;


    public Space(Long id, String name, String spaceId, Location location, SpaceType type, Price price, Address address, String features, Integer level, String dimensions, Integer minimumRentalPeriodHours, Integer maximumRentalPeriodHours, List<Review> reviews, List<AllocationTime> allocationTimes, String payment, User owner, String description, Access access, boolean storeOwnItems, boolean storeMultipleCustomersItems, String separationBetweenItems, Integer minimumTimeBeforeRenterArrivesHours, Float averageRating) {
        this.id = id;
        this.name = name;
        this.spaceId = spaceId;
        this.location = location;
        this.type = type;
        this.price = price;
        this.address = address;
        this.features = features;
        this.level = level;
        this.dimensions = dimensions;
        this.minimumRentalPeriodHours = minimumRentalPeriodHours;
        this.maximumRentalPeriodHours = maximumRentalPeriodHours;
        this.reviews = reviews;
        this.allocationTimes = allocationTimes;
        this.payment = payment;
        this.owner = owner;
        this.description = description;
        this.access = access;
        this.storeOwnItems = storeOwnItems;
        this.storeMultipleCustomersItems = storeMultipleCustomersItems;
        this.separationBetweenItems = separationBetweenItems;
        this.minimumTimeBeforeRenterArrivesHours = minimumTimeBeforeRenterArrivesHours;
        this.averageRating = averageRating;
    }





    public Space(Long id) {
        this.id = id;
    }


    @PrePersist
    public void setDefaultValues() {
        this.averageRating = 0.0f;
    }
}
