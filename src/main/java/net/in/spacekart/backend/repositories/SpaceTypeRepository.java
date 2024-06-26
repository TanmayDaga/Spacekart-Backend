package net.in.spacekart.backend.repositories;

import net.in.spacekart.backend.database.entities.SpaceType;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpaceTypeRepository extends JpaRepository<SpaceType, Long> {

}
