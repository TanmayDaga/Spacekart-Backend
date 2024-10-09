package net.in.spacekart.backend.services.entityServices;


import net.in.spacekart.backend.database.entities.Bid;
import net.in.spacekart.backend.database.entities.Price;
import net.in.spacekart.backend.payloads.get.bid.BidDetailDtoPublic;
import net.in.spacekart.backend.payloads.get.bid.BidDetailsDtoPrivate;
import net.in.spacekart.backend.payloads.post.bid.BidCreateDto;
import net.in.spacekart.backend.repositories.BidRepository;
import net.in.spacekart.backend.services.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BidService {



    @Autowired
    BidRepository bidRepository;

    private final UtilsService utilsService;

    public BidService(UtilsService utilsService) {
        this.utilsService = utilsService;
    }

    public void insertBid(BidCreateDto bidCreateDto, String username) {
        Price price = new Price();
        price.setRatePerHour(bidCreateDto.getPriceRatePerHour());
        price.setRatePerMonth(bidCreateDto.getPriceRatePerMonth());
        price.setSecurityDeposit(bidCreateDto.getPriceSecurityDeposit());
        Bid bid = new Bid();
            bid.setPrice(price);
            bid.setPublicId(utilsService.generateRandomId("bid" + username.substring(username.length() -4)));
            bid.setDescription(bidCreateDto.getDescription());
        bidRepository.insertBid(bid, username,bidCreateDto.getSpaceSpaceId(),bidCreateDto.getProposalPublicId());

    }

    public BidDetailsDtoPrivate getBidPrivate(String bidPublicId){
        return bidRepository.getBidPrivate(bidPublicId);
    }

    public BidDetailDtoPublic getBidPublic(String bidPublicId){
        return bidRepository.getBidPublic(bidPublicId);
    }

    public Page<String> getBidsPublicIdFromProposalPublicId(String proposalId, Pageable pageable){
        return bidRepository.getBidsPublicIdFromProposalPublicId(proposalId, pageable);
    }

    public Long getTotalBidsPublicId(String proposalId){
        return bidRepository.getTotalBidsPublicId(proposalId);
    }



}
