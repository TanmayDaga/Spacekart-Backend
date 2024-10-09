package net.in.spacekart.backend.repositories;

import net.in.spacekart.backend.database.entities.Space;
import net.in.spacekart.backend.database.entities.SpaceType;
import net.in.spacekart.backend.payloads.get.space.SpaceDetailsDtoPublic;
import net.in.spacekart.backend.payloads.get.space.SpaceDetailsPrivateDto;
import net.in.spacekart.backend.payloads.get.space.SpaceListDto;
import net.in.spacekart.backend.payloads.get.space.SpaceSummaryDtoPublic;
import net.in.spacekart.backend.payloads.put.space.SpacePutPrivateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface SpaceRepository {

    public Long insertByUserName(String username, Space space);


    public SpaceType findByName(String name);

    public Space getSpaceBySpaceId(String spaceId) ;

    public SpaceDetailsDtoPublic getSpaceDetailsDtoBySpaceId(String spaceId);

    public SpaceSummaryDtoPublic getSpaceSummary(String spaceId);

    public Page<String> getListOfSpaces(Pageable pageable);

    public List<String> getSpaceByUsername(String username);

    public void insertMedia(Long mediaId, Long spaceId);

    public void insertStreetViewMedia(Long mediaId, Long spaceId);

    public void deleteSpace(String spaceTypeId);

    public SpaceDetailsPrivateDto getSpaceDetailsPrivateDtoBySpaceId(String spaceId);


    public void deleteStreetViewByAssetId(String assetId);

    public void deleteMediaByAssetId(String assetId);

    public long getIdBySpaceId(String spaceId);

    public void putSpace(SpacePutPrivateDto spacePutPrivateDto, Long id);


    public List<SpaceListDto> getSpaceListDtoByUserId(String username);
}
