package net.in.spacekart.backend.repositories;

import net.in.spacekart.backend.database.entities.Price;

public interface PriceRepository {



    Long save(Price price);
    Price findById(Long id);
    void delete(Long id);
}
