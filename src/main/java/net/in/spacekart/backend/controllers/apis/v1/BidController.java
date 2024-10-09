package net.in.spacekart.backend.controllers.apis.v1;


import jakarta.validation.Valid;
import net.in.spacekart.backend.database.entities.UserAuthentication;
import net.in.spacekart.backend.payloads.post.bid.BidCreateDto;
import net.in.spacekart.backend.services.UtilsService;
import net.in.spacekart.backend.services.entityServices.BidService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/v1/bids")
public class BidController {


    private final BidService bidService;
    private final UtilsService utilsService;

    public BidController(BidService bidService, UtilsService utilsService) {
        this.bidService = bidService;
        this.utilsService = utilsService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<?> createBid(@Valid @ModelAttribute BidCreateDto bidCreateDto, Authentication authentication) {
        UserAuthentication userAuthentication = (UserAuthentication) authentication.getPrincipal();
        if(userAuthentication.getUsername().equals(utilsService.getUsernameProposalPublicId(bidCreateDto.getProposalPublicId()))){
            return new ResponseEntity<>("You are not allowed to create bid on your own proposal",HttpStatus.FORBIDDEN);
        }

        bidService.insertBid(bidCreateDto, userAuthentication.getUsername());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{publicId}")
    public ResponseEntity<?> getBidDetails(@PathVariable String publicId, Authentication authentication) {
        return new ResponseEntity<>((bidService.getBidPublic(publicId)), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me/{publicId}")
    public ResponseEntity<?> getBidDetailsPrivate(@PathVariable String publicId, Authentication authentication) {
        UserAuthentication userAuthentication = (UserAuthentication) authentication.getPrincipal();
        if (userAuthentication.getUsername().equals(utilsService.getUsernameBidPublicId(publicId))) {
            return new ResponseEntity<>(bidService.getBidPrivate(publicId), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/list/{proposalPublicId}/{pageNumber}/{pageSize}")
    public ResponseEntity<?> getBidsIdOfProposal(@PathVariable("proposalPublicId") String proposalId, @PathVariable Integer pageNumber, @PathVariable Integer pageSize) {
        if (pageNumber < 1 || pageSize < 1 || pageSize > 15) {
            return new ResponseEntity<>("Invalid Parameters Passed", HttpStatus.BAD_REQUEST);
        }

        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return new ResponseEntity<>(bidService.getBidsPublicIdFromProposalPublicId(proposalId, pageRequest).stream().toList(), HttpStatus.OK
        );
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/total/{proposalPublicId}")
    public ResponseEntity<?> getTotalBids(@PathVariable String proposalPublicId) {
        return new ResponseEntity<>(bidService.getTotalBidsPublicId(proposalPublicId), HttpStatus.OK);
    }


}
