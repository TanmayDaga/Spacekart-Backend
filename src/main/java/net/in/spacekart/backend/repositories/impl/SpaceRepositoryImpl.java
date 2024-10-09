package net.in.spacekart.backend.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import net.in.spacekart.backend.database.entities.Media;
import net.in.spacekart.backend.database.entities.Space;
import net.in.spacekart.backend.database.entities.SpaceType;
import net.in.spacekart.backend.database.entities.User;
import net.in.spacekart.backend.payloads.get.media.MediaPrivateDto;
import net.in.spacekart.backend.payloads.get.space.SpaceDetailsDtoPublic;
import net.in.spacekart.backend.payloads.get.space.SpaceDetailsPrivateDto;
import net.in.spacekart.backend.payloads.get.space.SpaceListDto;
import net.in.spacekart.backend.payloads.get.space.SpaceSummaryDtoPublic;
import net.in.spacekart.backend.payloads.put.space.SpacePutPrivateDto;
import net.in.spacekart.backend.payloads.space.SpaceDetailsDto;
import net.in.spacekart.backend.repositories.SpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Repository

public class SpaceRepositoryImpl implements SpaceRepository {


    @Autowired
    EntityManager entityManager;

    @Autowired
    SecureRandom secureRandom;


    @Override
    public SpaceDetailsDtoPublic getSpaceDetailsDtoBySpaceId(String spaceId) {
        SpaceDetailsDtoPublic spaceDetailsDto = entityManager.createQuery("select new net.in.spacekart.backend.payloads.get.space.SpaceDetailsDtoPublic(s.name, s.spaceId, s.location.latitude, s.location.longitude, s.type.name , s.price.ratePerMonth,s.price.ratePerHour,s.price.securityDeposit,s.address.line,s.address.landmark, s.address.city,s.address.state,s.address.postalCode,s.features,s.level,s.dimensions,s.minimumRentalPeriodHours,s.maximumRentalPeriodHours,s.payment,s.owner.firstName,s.owner.lastName,s.owner.username,s.description,s.access.entryTime,s.access.exitTime,s.access.accessType,s.storeOwnItems,s.storeMultipleCustomersItems,s.separationBetweenItems,s.minimumTimeBeforeRenterArrivesHours,s.averageRating) from Space  s where s.spaceId = :spaceId",SpaceDetailsDtoPublic.class).setParameter("spaceId",spaceId).getSingleResult();
        spaceDetailsDto.setPhotoUrls(entityManager.createNativeQuery("SELECT m.url FROM media m JOIN space_media sm ON m.id = sm.media_id JOIN space s ON sm.space_id = s.id WHERE s.space_id = ?", String.class).setParameter(1,spaceId).getResultList());
        spaceDetailsDto.setStreetViewUrls(entityManager.createNativeQuery("SELECT m.url FROM media m JOIN space_street_view sm ON m.id = sm.street_view_id JOIN space s ON sm.space_id = s.id WHERE s.space_id = ?", String.class).setParameter(1,spaceId).getResultList());
      try{
          String ownerPictureUrl = entityManager.createQuery("select s.owner.profilePicture.url from Space s where s.spaceId = :spaceId" , String.class).setParameter("spaceId",spaceId).getSingleResult();
          spaceDetailsDto.setOwnerProfilePictureUrl(ownerPictureUrl);

      }catch (Exception e){

      }

        return spaceDetailsDto;
    }

    @Transactional
    @Override
    public Long insertByUserName(String username, Space space) {


        Long userId = (Long) entityManager.createQuery("SELECT u.id FROM User u WHERE u.username = :username")
                .setParameter("username", username)
                .getSingleResult();


        if (userId != null) {
            User user = entityManager.getReference(User.class, userId);
            space.setOwner(user);
            Space space2 = entityManager.merge(space);

            return  space2.getId();
        }
        return  null;

    }

    @Override
    public SpaceType findByName(String name) {
        return entityManager.createQuery("select s from SpaceType s where name =:name ", SpaceType.class)
                .setParameter("name", name)
                .getSingleResult();
    }


    public Space getSpaceBySpaceId(String spaceId) {

        Space space = null;
        try {
            space = entityManager.createQuery("select s from Space s where s.spaceId = :spaceId", Space.class).setParameter("spaceId", spaceId).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return space;

    }


    @Override
    public SpaceSummaryDtoPublic getSpaceSummary(String spaceId) {
        SpaceSummaryDtoPublic spaceSummaryDtoPublic = null;
        try {
         spaceSummaryDtoPublic =    entityManager.createQuery("select new net.in.spacekart.backend.payloads.get.space.SpaceSummaryDtoPublic(s.name, s.spaceId, s.location.latitude, s.location.longitude, s.type.name,s.price.ratePerMonth, s.price.ratePerHour, s.price.securityDeposit, s.features, s.dimensions, s.owner.firstName, s.owner.lastName, s.owner.username, s.description, s.averageRating) from Space s where s.spaceId = :spaceId", SpaceSummaryDtoPublic.class).setParameter("spaceId", spaceId).getSingleResult();
         List<String> photoUrls = entityManager.createNativeQuery("SELECT m.url FROM media m JOIN space_media sm ON m.id = sm.media_id JOIN space s ON sm.space_id = s.id WHERE s.space_id = ?", String.class).setParameter(1,spaceId).getResultList();
         if(photoUrls!= null && photoUrls.size() > 0) {
             spaceSummaryDtoPublic.setPhotoUrls(photoUrls);
         }

        }catch (NoResultException e) {
            return null;
        }
        return spaceSummaryDtoPublic;
    }

    @Override
    public Page<String> getListOfSpaces(Pageable pageable) {

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        List<String> results = entityManager.createQuery("select s.spaceId from Space s").setFirstResult(pageNumber * pageSize).setMaxResults(pageSize).getResultList();
        Long countResult = (Long) entityManager.createQuery("select count(s.spaceId) from Space s").getSingleResult();

        return new PageImpl<>(results, pageable, countResult);
    }

    @Override
    public List<String> getSpaceByUsername(String username) {
        Long id = entityManager.createQuery("select u.id from User u where u.username = :username",Long.class).setParameter("username", username).getSingleResult();
        return entityManager.createNativeQuery("select space_id from space where owner_id = ?", String.class).setParameter(1, id).getResultList();

    }

    @Transactional
    @Override
    @Modifying
    public void insertMedia(Long mediaId, Long spaceId) {
        entityManager.createNativeQuery("insert into space_media (media_id, space_id) values (?, ?)").setParameter(2, mediaId).setParameter(2, spaceId).executeUpdate();
    }

    @Transactional
    @Override
    @Modifying
    public void insertStreetViewMedia(Long mediaId, Long spaceId) {
        entityManager.createNativeQuery("insert into space_street_view (media_id, space_id) values (?, ?)").setParameter(2, mediaId).setParameter(2, spaceId).executeUpdate();
    }

    @Transactional
    @Override
    public void deleteSpace(String spaceTypeId) {
        Long id = entityManager.createQuery("select s.id from Space s where s.spaceId = :spaceId", Long.class).setParameter("spaceId", spaceTypeId).getSingleResult();
        entityManager.remove(entityManager.getReference(Space.class, id));
    }

    @Transactional
    @Override
    public SpaceDetailsPrivateDto getSpaceDetailsPrivateDtoBySpaceId(String spaceId) {
        SpaceDetailsPrivateDto spaceDetailsPrivateDto = entityManager.createQuery("select new net.in.spacekart.backend.payloads.get.space.SpaceDetailsPrivateDto(s.name, s.location.latitude, s.location.longitude,s.type.name, s.price.ratePerMonth,s.price.ratePerHour,s.price.securityDeposit,s.address.line,s.address.landmark,s.address.city,s.address.state,s.address.postalCode,s.features,s.level,s.dimensions,s.minimumRentalPeriodHours,s.maximumRentalPeriodHours,s.description,s.access.entryTime,s.access.exitTime,s.access.accessType,s.storeOwnItems,s.storeMultipleCustomersItems,s.minimumTimeBeforeRenterArrivesHours,s.separationBetweenItems) from Space  s where s.spaceId = :spaceId", SpaceDetailsPrivateDto.class).setParameter("spaceId", spaceId).getSingleResult();

        List<Object[]> mediaResults = entityManager.createNativeQuery("SELECT m.url as url, m.asset_id as assetId FROM media m JOIN space_media sm ON m.id = sm.media_id JOIN space s ON sm.space_id = s.id WHERE s.space_id = ?").setParameter(1, spaceId).getResultList();

        List<MediaPrivateDto> mediaDtoList = new ArrayList<>();
        if(mediaResults != null && mediaResults.size() > 0) {
            for(Object[] row : mediaResults) {
                mediaDtoList.add(new MediaPrivateDto((String) row[0],(String) row[1] ));
            }
        }

        List<Object[]> streetViewResults = entityManager.createNativeQuery("SELECT m.url as url, m.asset_id as assetId FROM media m JOIN space_street_view sm ON m.id = sm.street_view_id JOIN space s ON sm.space_id = s.id WHERE s.space_id = ?").setParameter(1, spaceId).getResultList();

        List<MediaPrivateDto> streetViewDtoList = new ArrayList<>();
        if(streetViewResults != null && streetViewResults.size() > 0) {
            for(Object[] row : streetViewResults) {
                streetViewDtoList.add(new MediaPrivateDto((String) row[0],(String) row[1] ));
            }
        }

        spaceDetailsPrivateDto.setMedia(mediaDtoList);
        spaceDetailsPrivateDto.setStreetView(streetViewDtoList);

        return spaceDetailsPrivateDto;
    }


    @Transactional
    @Override
    @Modifying
    public void deleteStreetViewByAssetId(String assetId) {
        entityManager.createNativeQuery("DELETE FROM space_street_view ssv USING media m WHERE ssv.street_view_id = m.id AND m.asset_id = ?").setParameter(1, assetId).executeUpdate();
        Long id = entityManager.createQuery("SELECT m.id from Media m where m.assetId = :assetId ", Long.class).setParameter("assetId", assetId).getSingleResult();
        entityManager.remove(entityManager.getReference(Media.class, id));
    }

    @Transactional
    @Override
    @Modifying
    public void deleteMediaByAssetId(String assetId) {
        entityManager.createNativeQuery("DELETE FROM space_media ssv USING media m WHERE ssv.media_id = m.id AND m.asset_id = ?").setParameter(1, assetId).executeUpdate();
        Long id = entityManager.createQuery("SELECT m.id from Media m where m.assetId = :assetId ", Long.class).setParameter("assetId", assetId).getSingleResult();
        entityManager.remove(entityManager.getReference(Media.class, id));
    }

    @Transactional
    @Override
    public long getIdBySpaceId(String spaceId) {
        return  entityManager.createQuery("select s.id from Space s where s.spaceId = :spaceId", Long.class).setParameter("spaceId", spaceId).getSingleResult();
    }

    @Transactional
    @Override
    @Modifying
    public void putSpace(SpacePutPrivateDto spacePutPrivateDto,Long id) {
        Space s  = entityManager.getReference(Space.class, id);
        SpaceType spaceType = entityManager.createQuery("select new net.in.spacekart.backend.database.entities.SpaceType(st.id) from SpaceType  st where st.name = :name",SpaceType.class).setParameter("name",spacePutPrivateDto.getTypeName()).getSingleResult();
        s.setName(spacePutPrivateDto.getName());
        s.setType(spaceType);
        s.getPrice().setRatePerMonth(spacePutPrivateDto.getPriceRatePerMonth());
        s.getPrice().setRatePerHour(spacePutPrivateDto.getPriceRatePerHour());
        s.getPrice().setSecurityDeposit(spacePutPrivateDto.getPriceSecurityDeposit());
        s.getAddress().setLine(spacePutPrivateDto.getAddressLine());
        s.getAddress().setLandmark(spacePutPrivateDto.getAddressLandmark());
        s.getAddress().setCity(spacePutPrivateDto.getAddressCity());
        s.getAddress().setState(spacePutPrivateDto.getAddressState());
        s.getAddress().setPostalCode(spacePutPrivateDto.getAddressPostalCode());
        s.setFeatures(spacePutPrivateDto.getFeatures());
        s.setLevel(spacePutPrivateDto.getLevel());
        s.setDimensions(spacePutPrivateDto.getDimensions());
        s.setMinimumRentalPeriodHours(spacePutPrivateDto.getMinimumRentalPeriodHours());
        s.setMaximumRentalPeriodHours(spacePutPrivateDto.getMaximumRentalPeriodHours());
        s.setDescription(spacePutPrivateDto.getDescription());
        s.getAccess().setEntryTime(spacePutPrivateDto.getAccessEntryTime());
        s.getAccess().setExitTime(spacePutPrivateDto.getAccessExitTime());
        s.getAccess().setAccessType(spacePutPrivateDto.getAccessAccessType());
        s.setStoreOwnItems(spacePutPrivateDto.isStoreOwnItems());
        s.setStoreMultipleCustomersItems(spacePutPrivateDto.isStoreMultipleCustomersItems());
        s.setSeparationBetweenItems(spacePutPrivateDto.getSeparationBetweenItems());
        s.setMinimumTimeBeforeRenterArrivesHours(spacePutPrivateDto.getMinimumTimeBeforeRenterArrivesHours());

        entityManager.merge(s);
    }

    @Override
    public List<SpaceListDto> getSpaceListDtoByUserId(String username) {
        return entityManager.createQuery("select  new net.in.spacekart.backend.payloads.get.space.SpaceListDto(s.name, s.spaceId) from Space s where s.owner.username = :username",SpaceListDto.class).setParameter("username", username).getResultList();
    }


}


