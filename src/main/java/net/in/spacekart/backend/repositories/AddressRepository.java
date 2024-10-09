package net.in.spacekart.backend.repositories;

import net.in.spacekart.backend.database.entities.Address;
import net.in.spacekart.backend.database.entities.Price;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository {

    Long save(Price price);

    Address findById(Long id);

    void delete(Long id);
}


