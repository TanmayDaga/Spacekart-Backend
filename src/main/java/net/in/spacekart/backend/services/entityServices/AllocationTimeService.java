package net.in.spacekart.backend.services.entityServices;

import net.in.spacekart.backend.payloads.get.allocationTime.AllocationTimePrivateDto;
import net.in.spacekart.backend.payloads.get.allocationTime.AllocationTimePublicDTO;
import net.in.spacekart.backend.repositories.AllocationTimeRepository;
import net.in.spacekart.backend.repositories.SpaceRepository;
import net.in.spacekart.backend.repositories.UserRepository;
import net.in.spacekart.backend.services.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class AllocationTimeService {


    UserRepository userRepository;
    AllocationTimeRepository allocationTimeRepository;
    SpaceRepository spaceRepository;
    UtilsService utilsService;

    @Autowired
    public AllocationTimeService(UserRepository userRepository, AllocationTimeRepository allocationTimeRepository, SpaceRepository spaceRepository,UtilsService utilsService) {
        this.userRepository = userRepository;
        this.allocationTimeRepository = allocationTimeRepository;
        this.spaceRepository = spaceRepository;
        this.utilsService = utilsService;
    }


    public void save(String renterPublicId, String spacePublicId, OffsetDateTime startTime, OffsetDateTime endTime, String status) {
        allocationTimeRepository.save(userRepository.getIdByUsername(renterPublicId), spaceRepository.getIdBySpaceId(spacePublicId), startTime, endTime, status,
                utilsService.generateRandomId(renterPublicId.substring(0, 4) + spacePublicId.substring(0, 4))
        );
    }

    public List<AllocationTimePublicDTO> findPublicDTOByRenterId(String renterUsername, Integer month, Integer year) {
        return allocationTimeRepository.findPublicDTOByRenterId(renterUsername, month, year);
    }

    public List<AllocationTimePublicDTO> findPublicAllocationTimeDTOBySpaceId(String spaceId, Integer month, Integer year) {
        return allocationTimeRepository.findPublicAllocationTimeDTOBySpaceId(spaceId, month, year);
    }

    public List<AllocationTimePrivateDto> findPrivateDTOByRenterId(String spaceId, Integer month, Integer year) {
        return allocationTimeRepository.findPrivateDTOByRenterId(spaceId, month, year);
    }

    public void update(String publicId, String status, OffsetDateTime startTime, OffsetDateTime endTime) {
        allocationTimeRepository.update(publicId, status,  startTime, endTime);
    }

    public void delete(String allocationTimeId) {
        allocationTimeRepository.delete(allocationTimeId);
    }

    public boolean isBooked(String spaceId, OffsetDateTime startTime, OffsetDateTime endTime) {
        return allocationTimeRepository.isBooked(spaceId, startTime, endTime);
    }


}
