package net.in.spacekart.backend.database.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    private Double ratePerMonth;
    private Double ratePerHour;
    private Double securityDeposit;

    public Price(Long id) {
        this.id = id;
    }
}
