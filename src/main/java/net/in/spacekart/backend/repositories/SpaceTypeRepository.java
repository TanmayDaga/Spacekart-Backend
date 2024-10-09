package net.in.spacekart.backend.repositories;

import net.in.spacekart.backend.database.entities.SpaceType;
import net.in.spacekart.backend.payloads.get.spaceType.SpaceTypePublicDto;
import net.in.spacekart.backend.payloads.spaceType.SpaceTypeNameDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface SpaceTypeRepository {




    List<SpaceTypePublicDto> findPublicDtoAll();



    void changeNameByName(String name, String newName);

    Long getIdByName(String name);

    List<String> getSpaceTypeNames();




    Long save(SpaceType spaceType);


    void deleteByName(String name);

    void deleteMedia(String spaceTypeName);


}
