package net.in.spacekart.backend.repositories;

import net.in.spacekart.backend.database.entities.Proposal;
import net.in.spacekart.backend.payloads.get.proposal.ProposalDetailsDtoPrivate;
import net.in.spacekart.backend.payloads.get.proposal.ProposalDetailsDtoPublic;
import net.in.spacekart.backend.payloads.put.proposal.ProposalPutDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProposalRepository {

    public void insertProposal(Proposal proposal, String username);

    public ProposalDetailsDtoPublic getPublicProposal(String proposalPublicId);

    public ProposalDetailsDtoPrivate getPrivateProposal(String proposalPublicId);


    public Page<String> getProposalPublicIds(Pageable pageable);

    public String getUsernameFromProposalPublicId(String publicId);


    // Also deletes respective bids
    public void deleteProposal(String publicId);


    public  void putProposal(String publicId, ProposalPutDto proposalPutDto);


}
