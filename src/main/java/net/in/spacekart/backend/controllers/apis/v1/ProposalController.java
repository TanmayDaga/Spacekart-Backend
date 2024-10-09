package net.in.spacekart.backend.controllers.apis.v1;


import jakarta.validation.Valid;
import net.in.spacekart.backend.database.entities.UserAuthentication;
import net.in.spacekart.backend.payloads.post.proposal.ProposalCreateDto;
import net.in.spacekart.backend.payloads.put.proposal.ProposalPutDto;
import net.in.spacekart.backend.services.entityServices.ProposalService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/proposal")
public class ProposalController {


    private final ProposalService proposalService;

    public ProposalController(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<?> insertProposal(@Valid @ModelAttribute ProposalCreateDto proposalCreateDto, Authentication authentication) {
        UserAuthentication userAuthentication = (UserAuthentication) authentication.getPrincipal();
        proposalService.insertProposal(proposalCreateDto, userAuthentication.getUsername());
        return ResponseEntity.ok().build();
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{proposalId}")
    public ResponseEntity<?> getPublicProposal(@PathVariable String proposalId) {
        return new ResponseEntity<>(proposalService.getPublicProposal(proposalId), HttpStatus.OK);
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me/{proposalPublicId}")
    public ResponseEntity<?> getPrivateProposal(@PathVariable("proposalPublicId") String proposalId, Authentication authentication) {
        UserAuthentication userAuthentication = (UserAuthentication) authentication.getPrincipal();
        if (userAuthentication.getUsername().equals(proposalService.getUsernameFromProposalPublicId(proposalId))) {
            return new ResponseEntity<>(proposalService.getPrivateProposal(proposalId), HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/list/{pageSize}/{pageNumber}")
    public ResponseEntity<?> getProposalPublicIds(@PathVariable int pageSize, @PathVariable int pageNumber) {
        if (pageSize < 1 || pageSize > 15 || pageNumber < 1 || pageNumber > 15) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        PageRequest pageableRequest = PageRequest.of(pageNumber - 1, pageSize);
        return new ResponseEntity<>(proposalService.getProposalPublicIds(pageableRequest).stream().toList(), HttpStatus.OK);

    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/me/{proposalId}")
    public ResponseEntity<?> deleteProposal(@PathVariable String proposalId, Authentication authentication) {
        UserAuthentication userAuthentication = (UserAuthentication) authentication.getPrincipal();
        if(userAuthentication.getUsername().equals(proposalService.getUsernameFromProposalPublicId(proposalId))) {
            proposalService.deleteProposal(proposalId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/me/{proposalId}")
    public ResponseEntity<?> putProposal(@PathVariable String proposalId, Authentication authentication, @ModelAttribute @Valid ProposalPutDto proposalPutDto) {
        UserAuthentication userAuthentication = (UserAuthentication) authentication.getPrincipal();
        if(userAuthentication.getUsername().equals(proposalService.getUsernameFromProposalPublicId(proposalId))) {
            proposalService.putProposal(proposalPutDto, proposalId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }






}
