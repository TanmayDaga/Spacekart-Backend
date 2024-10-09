package net.in.spacekart.backend.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import net.in.spacekart.backend.database.entities.Bid;
import net.in.spacekart.backend.database.entities.Proposal;
import net.in.spacekart.backend.database.entities.Space;
import net.in.spacekart.backend.database.entities.User;
import net.in.spacekart.backend.payloads.get.bid.BidDetailDtoPublic;
import net.in.spacekart.backend.payloads.get.bid.BidDetailsDtoPrivate;
import net.in.spacekart.backend.repositories.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BidRepositoryImpl implements BidRepository {

    @Autowired
    EntityManager em;

    @Transactional
    @Override
    public void insertBid(Bid bid, String username, String spacePublicId, String proposalPublicId) {
        Proposal proposal = em.createQuery("select new Proposal(p.id) from Proposal p where p.publicId = :proposalPublicId", Proposal.class)
                .setParameter("proposalPublicId", proposalPublicId)
                .getSingleResult();

        User user = em.createQuery("select new User(u.id) from User u where u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();

        bid.setBidder(user);
        Space space = em.createQuery("select new Space(s.id) from Space s where s.spaceId = :spaceId", Space.class)
                .setParameter("spaceId", spacePublicId)
                .getSingleResult();

        bid.setSpace(space);
        bid.setProposal(proposal);
        em.persist(bid);
    }

    @Override
    public BidDetailDtoPublic getBidPublic(String bidPublicId) {
        return em.createQuery("select new net.in.spacekart.backend.payloads.get.bid.BidDetailDtoPublic(b.publicId,b.space.spaceId, b.description,b.bidder.username, b.price.ratePerMonth, b.price.ratePerHour, b.price.securityDeposit,b.createAt,b.updateAt,b.space.name,b.space.type.name, b.space.dimensions,b.space.averageRating,b.space.features)  from Bid b where b.publicId = :bidPublicId", BidDetailDtoPublic.class)
                .setParameter("bidPublicId", bidPublicId).getSingleResult();
    }

    @Override
    public BidDetailsDtoPrivate getBidPrivate(String bidPublicId) {
        return em.createQuery("select new net.in.spacekart.backend.payloads.get.bid.BidDetailsDtoPrivate(b.publicId,b.space.spaceId, b.description,b.bidder.username, b.price.ratePerMonth, b.price.ratePerHour, b.price.securityDeposit,b.createAt,b.updateAt)  from Bid b where b.publicId = :bidPublicId", BidDetailsDtoPrivate.class)
                .setParameter("bidPublicId", bidPublicId).getSingleResult();
    }

    @Override
    public Page<String> getBidsPublicIdFromProposalPublicId(String proposalPublicId, Pageable pageable) {


       List<String> bidsPublicIds =  em.createQuery("select  b.publicId from Bid b where b.proposal.publicId = :publicId ", String.class).setParameter("publicId", proposalPublicId).setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
               .setMaxResults(pageable.getPageSize()).getResultList();


        return new PageImpl<>(bidsPublicIds, pageable, bidsPublicIds.size());


    }

    @Override
    public Long getTotalBidsPublicId(String proposalPublicId) {
        return em.createQuery("select  count(1) from Bid  b where b.proposal.publicId = :publicId", Long.class).setParameter("publicId", proposalPublicId).getSingleResult();
    }
}
