package net.in.spacekart.backend.database.entities;

import jakarta.persistence.*;


import lombok.*;
import net.in.spacekart.backend.constraints.Rating;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User myUserId;

    @Rating
    private Float rating;


}
