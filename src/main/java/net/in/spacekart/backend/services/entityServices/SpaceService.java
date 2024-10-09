package net.in.spacekart.backend.services.entityServices;


import net.in.spacekart.backend.database.embeddable.Access;
import net.in.spacekart.backend.database.embeddable.Location;
import net.in.spacekart.backend.database.entities.Address;
import net.in.spacekart.backend.database.entities.Price;
import net.in.spacekart.backend.database.entities.Space;
import net.in.spacekart.backend.exceptions.CustomMethodNotArgumentValidException;
import net.in.spacekart.backend.payloads.get.space.SpaceDetailsDtoPublic;
import net.in.spacekart.backend.payloads.get.space.SpaceDetailsPrivateDto;
import net.in.spacekart.backend.payloads.get.space.SpaceListDto;
import net.in.spacekart.backend.payloads.get.space.SpaceSummaryDtoPublic;
import net.in.spacekart.backend.payloads.post.space.SpaceCreateDto;
import net.in.spacekart.backend.payloads.put.space.SpacePutPrivateDto;
import net.in.spacekart.backend.repositories.SpaceRepository;
import net.in.spacekart.backend.services.MediaService;
import net.in.spacekart.backend.services.UtilsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpaceService {

    private final ModelMapper modelMapper;
    private final SpaceRepository spaceRepository;
    private final UtilsService utilsService;
    private final CommandLineRunner commandLineRunner;
    private final MediaService mediaService;

    @Autowired
    public SpaceService(ModelMapper modelMapper, SpaceRepository spaceRepository, UtilsService utilsService, CommandLineRunner commandLineRunner, MediaService mediaService) {
        this.modelMapper = modelMapper;
        this.spaceRepository = spaceRepository;
        this.utilsService = utilsService;
        this.commandLineRunner = commandLineRunner;
        this.mediaService = mediaService;
    }


    public String insertByUserName(String username, SpaceCreateDto spaceDetailsDtoPrivate) {

        if (spaceDetailsDtoPrivate.getPriceRatePerHour() == null && spaceDetailsDtoPrivate.getPriceRatePerMonth() == null) {
            throw new CustomMethodNotArgumentValidException("Invalid prices . Please enter at least on type of price");
        }


        Space space = new Space(null, spaceDetailsDtoPrivate.getName(), utilsService.generateRandomId("space" + username), new Location(spaceDetailsDtoPrivate.getLocationLatitude(), spaceDetailsDtoPrivate.getLocationLongitude()), spaceRepository.findByName(spaceDetailsDtoPrivate.getTypeName()), new Price(null, spaceDetailsDtoPrivate.getPriceRatePerMonth(), spaceDetailsDtoPrivate.getPriceRatePerHour(), spaceDetailsDtoPrivate.getPriceSecurityDeposit()), new Address(null, spaceDetailsDtoPrivate.getAddressLine(), spaceDetailsDtoPrivate.getAddressLandmark(), spaceDetailsDtoPrivate.getAddressCity(), spaceDetailsDtoPrivate.getAddressState(), spaceDetailsDtoPrivate.getAddressPostalCode()), spaceDetailsDtoPrivate.getFeatures(), spaceDetailsDtoPrivate.getLevel(), spaceDetailsDtoPrivate.getDimensions(), spaceDetailsDtoPrivate.getMinimumRentalPeriodHours(), spaceDetailsDtoPrivate.getMaximumRentalPeriodHours(), null, null, spaceDetailsDtoPrivate.getPayment(), null, spaceDetailsDtoPrivate.getDescription(), new Access(spaceDetailsDtoPrivate.getAccessEntryTime(), spaceDetailsDtoPrivate.getAccessExitTime(), spaceDetailsDtoPrivate.getAccessAccessType()), spaceDetailsDtoPrivate.isStoreOwnItems(), spaceDetailsDtoPrivate.isStoreMultipleCustomersItems(), spaceDetailsDtoPrivate.getSeparationBetweenItems(), spaceDetailsDtoPrivate.getMinimumTimeBeforeRenterArrivesHours(), 0.0f);


        Long id = spaceRepository.insertByUserName(username, space);


        if (spaceDetailsDtoPrivate.getPhotos() != null) {
            byte[][] mediaFiles = new byte[spaceDetailsDtoPrivate.getPhotos().length][];
            for (int i = 0; i < spaceDetailsDtoPrivate.getPhotos().length; i++) {
                try {
                    mediaFiles[i] = spaceDetailsDtoPrivate.getPhotos()[i].getBytes();
                } catch (Exception ignored) {
                }

            }
            mediaService.insertMedia(mediaFiles, id, MediaService.MediaType.MULTIPLE_SPACE_MEDIA);
        }

        if (spaceDetailsDtoPrivate.getStreetView() != null) {
            byte[][] mediaFiles = new byte[spaceDetailsDtoPrivate.getStreetView().length][];
            for (int i = 0; i < spaceDetailsDtoPrivate.getStreetView().length; i++) {
                try {
                    mediaFiles[i] = spaceDetailsDtoPrivate.getStreetView()[i].getBytes();
                } catch (Exception ignored) {
                }

            }
            mediaService.insertMedia(mediaFiles, id, MediaService.MediaType.MULTIPLE_SPACE_STREET_VIEW);
        }


        return space.getSpaceId();
    }


    public SpaceDetailsDtoPublic getSpaceDetailsDtoPublicBySpaceId(String spaceId) {


        return spaceRepository.getSpaceDetailsDtoBySpaceId(spaceId);


    }


    public Page<String> getListOfSpaces(Pageable pageable) {
        return spaceRepository.getListOfSpaces(pageable);
    }

    public SpaceSummaryDtoPublic getSpaceSummaryDtoPublicBySpaceId(String spaceId) {
        return spaceRepository.getSpaceSummary(spaceId);
    }

    public List<String> getSpaceIdsFromUsername(String username) {
        return spaceRepository.getSpaceByUsername(username);
    }


    public void deleteSpace(String spaceId) {

        spaceRepository.deleteSpace(spaceId);
    }


    public SpaceDetailsPrivateDto getSpaceDetailsPrivateBySpaceId(String spaceId) {
        return spaceRepository.getSpaceDetailsPrivateDtoBySpaceId(spaceId);
    }

    public void putSpace(String spaceId, SpacePutPrivateDto spacePutPrivateDto) {

        Long id = spaceRepository.getIdBySpaceId(spaceId);

        if (spacePutPrivateDto.getStreetViewAssetIdsToDelete() != null) {
            for (String assetId : spacePutPrivateDto.getStreetViewAssetIdsToDelete()) {
                System.out.println(assetId);
                spaceRepository.deleteStreetViewByAssetId(assetId);
            }
        }
        if (spacePutPrivateDto.getMediaAssetIdsToDelete() != null) {
            for (String assetId : spacePutPrivateDto.getMediaAssetIdsToDelete()) {
                spaceRepository.deleteMediaByAssetId(assetId);
            }
        }
        try {
            byte[][] mediaFiles = new byte[spacePutPrivateDto.getMediaNewFile().length][];

            for (int i = 0; i < spacePutPrivateDto.getMediaNewFile().length; i++) {
                mediaFiles[i] = spacePutPrivateDto.getMediaNewFile()[i].getBytes();
            }


            mediaService.insertMedia(mediaFiles, id, MediaService.MediaType.MULTIPLE_SPACE_MEDIA);

        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        try {
            byte[][] streetViewFiles = new byte[spacePutPrivateDto.getStreetViewNewFile().length][];
            for (int i = 0; i < spacePutPrivateDto.getStreetViewNewFile().length; i++) {
                streetViewFiles[i] = spacePutPrivateDto.getStreetViewNewFile()[i].getBytes();
            }


            System.out.println("Hello world");
            mediaService.insertMedia(streetViewFiles, id, MediaService.MediaType.MULTIPLE_SPACE_STREET_VIEW);

        } catch (Exception ignored) {
        ignored.printStackTrace();
        }

        spaceRepository.putSpace(spacePutPrivateDto, id);

    }

    public List<SpaceListDto> getSpaceListDtoByUserId(String username) {
        return spaceRepository.getSpaceListDtoByUserId(username);
    }
}
