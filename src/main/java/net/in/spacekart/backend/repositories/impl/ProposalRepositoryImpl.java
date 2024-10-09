package net.in.spacekart.backend.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import net.in.spacekart.backend.database.entities.Proposal;
import net.in.spacekart.backend.database.entities.User;
import net.in.spacekart.backend.payloads.get.proposal.ProposalDetailsDtoPrivate;
import net.in.spacekart.backend.payloads.get.proposal.ProposalDetailsDtoPublic;
import net.in.spacekart.backend.payloads.put.proposal.ProposalPutDto;
import net.in.spacekart.backend.repositories.ProposalRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ProposalRepositoryImpl implements ProposalRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    @Override
    public void insertProposal(Proposal proposal, String username) {
        User user = entityManager.createQuery("SELECT new User(u.id) from User u where username = :username",User.class).setParameter("username", username).getSingleResult();
        proposal.setRenter(user);
        entityManager.persist(proposal);
    }

    @Override
    public ProposalDetailsDtoPublic getPublicProposal(String proposalPublicId) {
        return entityManager.createQuery("select new net.in.spacekart.backend.payloads.get.proposal.ProposalDetailsDtoPublic(p.publicId, p.renter.username, p.createdAt,p.updatedAt, p.description,p.renter.profilePicture.url) from Proposal  p where  p.publicId = :publicId", ProposalDetailsDtoPublic.class).setParameter("publicId", proposalPublicId).getSingleResult();
    }

    @Override
    public ProposalDetailsDtoPrivate getPrivateProposal(String proposalPublicId) {
        return entityManager.createQuery("select new net.in.spacekart.backend.payloads.get.proposal.ProposalDetailsDtoPrivate(p.publicId, p.renter.username, p.createdAt, p.updatedAt, p.description) from Proposal  p where  p.publicId = :publicId" ,ProposalDetailsDtoPrivate.class).setParameter("publicId", proposalPublicId).getSingleResult();
    }

    @Override
    public Page<String> getProposalPublicIds(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();
        List<String> proposalIds = entityManager.createQuery("select p.publicId from Proposal  p ", String.class).setFirstResult((pageNumber) * pageSize).setMaxResults(pageSize).getResultList();
        return new PageImpl<>(proposalIds, pageable, proposalIds.size());
    }

    @Override
    public String getUsernameFromProposalPublicId(String publicId) {
        return entityManager.createQuery("SELECT  p.renter.username from Proposal p  where p.publicId = :publicId", String.class).setParameter("publicId", publicId).getSingleResult();
    }

    @Modifying
    @Override
    public void deleteProposal(String publicId) {
        Long id = entityManager.createQuery("select p.id from Proposal  p where p.publicId = :publicId", Long.class).setParameter("publicId", publicId).getSingleResult();
        Proposal proposal = entityManager.getReference(Proposal.class, id);
        entityManager.remove(proposal);

    }

    @Modifying
    @Override
    @Transactional
    public void putProposal(String publicId, ProposalPutDto proposalPutDto) {
        Long id  = entityManager.createQuery("select p.id from Proposal p where p.publicId =:publicId", Long.class).setParameter("publicId", publicId).getSingleResult();
        Proposal proposal = entityManager.getReference(Proposal.class, id);
        proposal.setDescription(proposalPutDto.getDescription());
        entityManager.merge(proposal);
    }
}

