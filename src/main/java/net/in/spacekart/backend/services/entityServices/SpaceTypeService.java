package net.in.spacekart.backend.services.entityServices;


import net.in.spacekart.backend.database.entities.SpaceType;
import net.in.spacekart.backend.payloads.delete.spacetype.SpaceTypeDeleteDto;
import net.in.spacekart.backend.payloads.get.spaceType.SpaceTypePublicDto;
import net.in.spacekart.backend.payloads.post.spaceType.SpaceTypeCreateDto;
import net.in.spacekart.backend.payloads.put.SpaceTypeUpdateDto;
import net.in.spacekart.backend.repositories.MediaRepository;
import net.in.spacekart.backend.repositories.SpaceTypeRepository;
import net.in.spacekart.backend.services.MediaService;
import net.in.spacekart.backend.services.UtilsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SpaceTypeService {


    private final ModelMapper modelMapper;
    private final SpaceTypeRepository spaceTypeRepository;
    private final UtilsService utilsService;
    MediaService mediaService;
    MediaRepository mediaRepository;

    @Autowired
    public SpaceTypeService(ModelMapper modelMapper, SpaceTypeRepository spaceTypeRepository, UtilsService utilsService, MediaService mediaService,MediaRepository mediaRepository) {
        this.modelMapper = modelMapper;
        this.spaceTypeRepository = spaceTypeRepository;
        this.utilsService = utilsService;
        this.mediaService = mediaService;
        this.mediaRepository = mediaRepository;
    }


    public void save(SpaceTypeCreateDto spaceType) {


        Long id = spaceTypeRepository.save(SpaceType.builder().name(spaceType.getName()).build());

        if (spaceType.getImage() != null) {
            try {
                byte[][] array = {spaceType.getImage().getBytes()};

                mediaService.insertMedia(array, id, MediaService.MediaType.SINGLE_SPACE_TYPE_PICTURE);
            } catch (Exception ignored) {
            }

        }

    }


    public List<SpaceTypePublicDto> findAll() {
        return spaceTypeRepository.findPublicDtoAll();
    }

    public void delete(SpaceTypeDeleteDto spaceTypeDeleteDto) {
        spaceTypeRepository.deleteByName(spaceTypeDeleteDto.getName());
    }

    public void update(SpaceTypeUpdateDto spaceTypeUpdateDto) {


        if (spaceTypeUpdateDto.getFile() != null) {
            Long spaceTypeId = spaceTypeRepository.getIdByName(spaceTypeUpdateDto.getOldName());
            spaceTypeRepository.deleteMedia(spaceTypeUpdateDto.getOldName());
            try {
                byte[][] arr = {spaceTypeUpdateDto.getFile().getBytes()};
                mediaService.insertMedia(arr, spaceTypeId, MediaService.MediaType.SINGLE_SPACE_TYPE_PICTURE);
            } catch (Exception ignore) {

            }
        }
        spaceTypeRepository.changeNameByName(spaceTypeUpdateDto.getOldName(), spaceTypeUpdateDto.getNewName());
    }

    public List<String> getNamesList() {
        return spaceTypeRepository.getSpaceTypeNames();
    }


}
