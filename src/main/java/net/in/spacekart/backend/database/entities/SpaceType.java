package net.in.spacekart.backend.database.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class SpaceType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Name cannot be empty")
    @Column(unique = true, nullable = false)
    private String name;


    @OneToOne(cascade =  CascadeType.ALL)
    private Media imageUrl;



}
