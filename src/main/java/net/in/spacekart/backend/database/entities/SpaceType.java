package net.in.spacekart.backend.database.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
public class SpaceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name=cannot be empty")
    @Column(unique = true, nullable = false)
    private String name;


    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    private Media imageUrl;


    public SpaceType(Long id) {
        this.id = id;
    }
}
