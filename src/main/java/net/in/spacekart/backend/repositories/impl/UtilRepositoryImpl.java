package net.in.spacekart.backend.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import net.in.spacekart.backend.repositories.UtilRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UtilRepositoryImpl implements UtilRepository {


    @PersistenceContext
    EntityManager entityManager;

    @Override
    public String getUsernameSpacePublicId(String publicSpaceId) {
        return entityManager.createQuery("select s.owner.username from Space s where s.spaceId = :publicSpaceId", String.class).setParameter("publicSpaceId",publicSpaceId).getSingleResult();
    }

    @Override
    public String getUsernameReviewPublicId(String publicReviewId) {
        return entityManager.createQuery("select r.reviewer.username from Review r where r.publicId = :publicReviewId", String.class).setParameter("publicReviewId",publicReviewId).getSingleResult();
    }

    @Override
    public String getUsernameBidPublicId(String publicBidId) {
        return entityManager.createQuery("select b.bidder.username from Bid b where b.publicId = :publicBidId", String.class).setParameter("publicBidId", publicBidId).getSingleResult();
    }

    @Override
    public String getUsernameProposalPublicId(String proposalId) {
        return entityManager.createQuery("select p.renter.username from Proposal p where  p.publicId = :publicProposalId", String.class).setParameter("publicProposalId",proposalId).getSingleResult();
    }

    @Override
    public String getUsernameAllocationTimePublicId(String allocationTimeId) {
        return entityManager.createQuery("select at.space.owner.username from AllocationTime at where at.publicId = :publicId",String.class).setParameter("publicId",allocationTimeId).getSingleResult();
    }
}
