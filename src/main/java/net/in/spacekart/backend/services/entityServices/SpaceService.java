package net.in.spacekart.backend.services.entityServices;


import net.in.spacekart.backend.repositories.SpaceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class SpaceService {

    private final ModelMapper modelMapper;
    private final SpaceRepository spaceRepository;

    public SpaceService(ModelMapper modelMapper, SpaceRepository spaceRepository) {
        this.modelMapper = modelMapper;
        this.spaceRepository = spaceRepository;
    }



}
