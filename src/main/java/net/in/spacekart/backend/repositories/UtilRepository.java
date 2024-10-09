package net.in.spacekart.backend.repositories;

public interface UtilRepository {


    public String  getUsernameSpacePublicId(String publicSpaceId);
    public String  getUsernameReviewPublicId(String publicReviewId);
    public String  getUsernameBidPublicId(String publicBidId);
    public String  getUsernameProposalPublicId(String proposalId);
    public String  getUsernameAllocationTimePublicId(String allocationTimeId);
}
