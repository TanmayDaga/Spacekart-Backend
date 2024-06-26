package net.in.spacekart.backend.database.entities;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import net.in.spacekart.backend.database.embeddable.Access;
import jakarta.persistence.*;

import java.sql.Time;
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


    @OneToOne
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
    private Time minimumRentalPeriod;

    private Time maximumRentalPeriod;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(cascade = CascadeType.ALL)
    private List<AllocationTime> allocationTimes;

    private String payment;


    @ManyToOne
    private User owner;


    @OneToMany(cascade = CascadeType.ALL)
    private List<Media> photos;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Media> streetView;

    @Column(nullable = false)
    @NotBlank(message = "Description cannot be Empty")
    private  String description;

    @Embedded
    private Access access;

    private boolean storeOwnItems;

    private boolean storeMultipleCustomersItems;

    private String separationBetweenItems;

    private Time minimumTimeBeforeRenterArrives;



}
