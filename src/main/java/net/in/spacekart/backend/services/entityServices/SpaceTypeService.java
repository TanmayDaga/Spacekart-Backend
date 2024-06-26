package net.in.spacekart.backend.services.entityServices;

import jakarta.transaction.Transactional;
import net.in.spacekart.backend.database.entities.SpaceType;
import net.in.spacekart.backend.payloads.spaceType.GuestSpaceTypeDto;
import net.in.spacekart.backend.repositories.SpaceTypeRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpaceTypeService {


    private final ModelMapper modelMapper;
    private final SpaceTypeRepository spaceTypeRepository;

    @Autowired
    public SpaceTypeService(ModelMapper modelMapper, SpaceTypeRepository spaceTypeRepository) {
        this.modelMapper = modelMapper;
        this.spaceTypeRepository = spaceTypeRepository;
    }


    @Transactional
    public void save(GuestSpaceTypeDto spaceType) {
        SpaceType spaceTypeEntity = modelMapper.map(spaceType, SpaceType.class);
        spaceTypeRepository.save(spaceTypeEntity);
    }

    @Transactional
    public List<GuestSpaceTypeDto> getAll() {
        List<SpaceType> spaceTypes = spaceTypeRepository.findAll();
        return modelMapper.map(spaceTypes, (new TypeToken<List<GuestSpaceTypeDto>>() {}).getType());

    }




}
