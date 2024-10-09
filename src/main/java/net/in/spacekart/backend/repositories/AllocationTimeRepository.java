package net.in.spacekart.backend.repositories;

import net.in.spacekart.backend.payloads.get.allocationTime.AllocationTimePrivateDto;
import net.in.spacekart.backend.payloads.get.allocationTime.AllocationTimePublicDTO;

import java.time.OffsetDateTime;
import java.util.List;


public interface AllocationTimeRepository {


    void save(Long renterId, Long spaceId, OffsetDateTime startTime, OffsetDateTime endTime, String status, String publicId);

    List<AllocationTimePublicDTO> findPublicDTOByRenterId(String renterUsername, Integer month, Integer year);

    List<AllocationTimePublicDTO> findPublicAllocationTimeDTOBySpaceId(String spaceId, Integer month, Integer year);

    List<AllocationTimePrivateDto> findPrivateDTOByRenterId(String spaceId, Integer month, Integer year);


    void update(String publicId, String status, OffsetDateTime startTime, OffsetDateTime endTime);

    void delete(String allocationTimeId);

    boolean isBooked(String spaceId, OffsetDateTime startTime, OffsetDateTime endTime);

}
