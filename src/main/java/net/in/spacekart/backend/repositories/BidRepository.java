package net.in.spacekart.backend.repositories;

import net.in.spacekart.backend.database.entities.Bid;
import net.in.spacekart.backend.database.entities.Proposal;
import net.in.spacekart.backend.payloads.get.bid.BidDetailDtoPublic;
import net.in.spacekart.backend.payloads.get.bid.BidDetailsDtoPrivate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BidRepository {


    public void insertBid(Bid bid, String username,
                          String spaceId, String proposalPublicId);


    public BidDetailDtoPublic getBidPublic(String bidPublicId);

    public BidDetailsDtoPrivate getBidPrivate(String bidPublicId);

    public Page<String> getBidsPublicIdFromProposalPublicId(String proposalPublicId, Pageable pageable);


    public Long getTotalBidsPublicId(String proposalPublicId);
}
