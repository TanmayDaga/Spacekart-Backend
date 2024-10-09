package net.in.spacekart.backend.database.entities;


import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.in.spacekart.backend.ViewSpaceKart;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@JsonView(ViewSpaceKart.UserAuthenticated.class)
public class Address {

    public Address(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    @NotBlank(message = "first=line of address cannot be blank")
    private String line;
    private String landmark;

    @Column(nullable = false)
    @NotBlank(message = "City=cannot be empty")
    private String city;

    @Column(nullable = false)
    @NotBlank(message = "State=cannot be empty")
    private String state;

    @Column(nullable = false)
    @NotBlank(message = "Postal=Code cannot be Empty")
    private String postalCode;

}
