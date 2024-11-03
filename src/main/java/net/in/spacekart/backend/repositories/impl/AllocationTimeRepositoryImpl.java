package net.in.spacekart.backend.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import net.in.spacekart.backend.database.entities.AllocationTime;
import net.in.spacekart.backend.database.entities.Space;
import net.in.spacekart.backend.database.entities.User;
import net.in.spacekart.backend.payloads.get.allocationTime.AllocationTimePrivateDto;
import net.in.spacekart.backend.payloads.get.allocationTime.AllocationTimePublicDTO;
import net.in.spacekart.backend.repositories.AllocationTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public class AllocationTimeRepositoryImpl implements AllocationTimeRepository {


    EntityManager em;

    @Autowired
    public AllocationTimeRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public void save(Long renterId, Long spaceId, OffsetDateTime startTime, OffsetDateTime endTime, String status, String publicId) {

        User user = em.find(User.class, renterId);
        Space space = em.find(Space.class, spaceId);
        AllocationTime allocationTime = new AllocationTime(publicId, startTime, endTime,user, status, space);
        em.persist(allocationTime);
    }

    @Override
    @Transactional
    public List<AllocationTimePublicDTO> findPublicDTOByRenterId(String renterUsername, Integer month, Integer year) {

        return em.createQuery("select new net.in.spacekart.backend.payloads.get.allocationTime.AllocationTimePublicDTO(at.publicId, at.startTime, at.endTime, at.status, at.space.spaceId) from AllocationTime at where at.renter.username = :username and month(at.startTime) = :month and year(at.startTime) = :year", AllocationTimePublicDTO.class)
                .setParameter("username", renterUsername)
                .setParameter("month", month)
                .setParameter("year", year)
                .getResultList();
    }

    @Override
    @Transactional
    public List<AllocationTimePublicDTO> findPublicAllocationTimeDTOBySpaceId(String spaceId, Integer month, Integer year) {
        return em.createQuery("select  new net.in.spacekart.backend.payloads.get.allocationTime.AllocationTimePublicDTO(at.publicId, at.startTime, at.endTime, at.status, :spaceId) from AllocationTime at where at.space.spaceId = :spaceId and month(at.startTime)  = :month and year(at.startTime) = :year", AllocationTimePublicDTO.class)
                .setParameter("spaceId", spaceId)
                .setParameter("month", month)
                .setParameter("year", year)
                .getResultList();
    }


    @Override
    @Transactional
    public List<AllocationTimePrivateDto> findPrivateDTOByRenterId(String spaceId, Integer month, Integer year) {
        return  em.createQuery("select new net.in.spacekart.backend.payloads.get.allocationTime.AllocationTimePrivateDto(at.publicId, at.startTime,at.endTime, at.renter.username, at.status, :spaceId) from AllocationTime  at where at.space.spaceId = :spaceId and month(at.startTime) = :month and year(at.startTime) = :year", AllocationTimePrivateDto.class)
                .setParameter("spaceId", spaceId)
                .setParameter("month", month)
                .setParameter("year", year)
                .getResultList();
    }

    @Override
    @Transactional
    public void update(String publicId, String status, OffsetDateTime startTime, OffsetDateTime endTime) {
        Long id = em.createQuery("select at.id from AllocationTime at where at.publicId = :publicId", Long.class)
                .setParameter("publicId", publicId).getSingleResult();

        AllocationTime allocationTime = em.find(AllocationTime.class, id);
        allocationTime.setStatus(status);
        allocationTime.setStartTime(startTime);
        allocationTime.setEndTime(endTime);
        em.merge(allocationTime);
    }

    @Override
    @Transactional
    public void delete(String allocationTimeId) {
        Long id = em.createQuery("select at.id from AllocationTime  at where  at.publicId = :allocationTimeId",Long.class)
                .setParameter("allocationTimeId",allocationTimeId)
                .getSingleResult();
        AllocationTime at = em.find(AllocationTime.class, id);
        em.remove(at);
    }

    @Override
    @Transactional
    public boolean isBooked(String spaceId, OffsetDateTime startTime, OffsetDateTime endTime) {
        Long count = em.createQuery("select count(at) from AllocationTime at where  at.space.spaceId = :spaceId and not ((at.startTime <:startTime and at.endTime>:startTime) or (at.startTime>:startTime and at.startTime<:endTime))",Long.class)
                .setParameter("startTime", startTime)
                .setParameter("endTime", endTime)
                .setParameter("spaceId", spaceId)
                .getSingleResult();

        return !(count==0);
    }
}
