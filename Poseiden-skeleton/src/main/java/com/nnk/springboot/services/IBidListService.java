package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;

import java.util.List;

public interface IBidListService {
    List<BidList> getBidLists();

    BidList getBidListById(Integer bidListId);

    BidList addBidList(BidList bidList);

    BidList updateBidList(BidList bidList);

    String deleteBidList(BidList bidList);
}
