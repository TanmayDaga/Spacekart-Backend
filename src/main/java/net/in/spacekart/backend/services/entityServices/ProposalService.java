package net.in.spacekart.backend.services.entityServices;

import net.in.spacekart.backend.database.entities.Proposal;
import net.in.spacekart.backend.payloads.get.proposal.ProposalDetailsDtoPrivate;
import net.in.spacekart.backend.payloads.get.proposal.ProposalDetailsDtoPublic;
import net.in.spacekart.backend.payloads.post.proposal.ProposalCreateDto;
import net.in.spacekart.backend.payloads.put.proposal.ProposalPutDto;
import net.in.spacekart.backend.repositories.ProposalRepository;
import net.in.spacekart.backend.services.UtilsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProposalService {


    private final UtilsService utilsService;


    private final ProposalRepository proposalRepository;

    public ProposalService(UtilsService utilsService, ProposalRepository proposalRepository) {
        this.utilsService = utilsService;
        this.proposalRepository = proposalRepository;
    }

    public void insertProposal(ProposalCreateDto createProposal, String username) {
        Proposal proposal = new Proposal();
        proposal.setDescription(createProposal.getDescription());
        proposal.setPublicId(utilsService.generateRandomId("proposal" + username.substring("proposal".length())));
        proposalRepository.insertProposal(proposal, username);
    }

    public ProposalDetailsDtoPublic getPublicProposal(String proposalId) {
        return proposalRepository.getPublicProposal(proposalId);

    }

    public ProposalDetailsDtoPrivate getPrivateProposal(String proposalId) {
        return proposalRepository.getPrivateProposal(proposalId);
    }

    public Page<String> getProposalPublicIds(Pageable pageable) {
        return proposalRepository.getProposalPublicIds(pageable);
    }


    public String getUsernameFromProposalPublicId(String publicId) {
        return proposalRepository.getUsernameFromProposalPublicId(publicId);
    }

    public void putProposal(ProposalPutDto proposal, String publicId) {
        proposalRepository.putProposal(publicId, proposal);
    }

    public void deleteProposal(String proposalId) {
        proposalRepository.deleteProposal(proposalId);
    }


}
