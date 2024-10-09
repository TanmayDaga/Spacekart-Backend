package net.in.spacekart.backend.repositories.impl;

import jakarta.persistence.EntityManager;
import net.in.spacekart.backend.database.entities.Price;
import net.in.spacekart.backend.repositories.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PriceRepositoryImpl implements PriceRepository {



    EntityManager em;

    @Autowired
    public PriceRepositoryImpl( EntityManager em ) {
        this.em = em;
    }

    @Override
    public Long save(Price price) {
        em.persist(price);
        return price.getId();
    }

    @Override
    public Price findById(Long id) {
      return em.find(Price.class, id);
    }

    @Override
    public void delete(Long id) {
        em.createQuery("delete from Price p where p.id = :id").setParameter("id", id).executeUpdate();
    }
}
