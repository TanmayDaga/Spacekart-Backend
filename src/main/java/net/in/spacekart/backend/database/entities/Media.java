package net.in.spacekart.backend.database.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Empty images url cannot be stored")
    private String url;
}
