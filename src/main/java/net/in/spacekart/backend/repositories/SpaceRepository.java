package net.in.spacekart.backend.repositories;

import net.in.spacekart.backend.database.entities.Space;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceRepository extends PagingAndSortingRepository<Space, Long> {

}
