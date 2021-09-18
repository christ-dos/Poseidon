package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;

import java.util.List;
import java.util.Optional;

public interface IBidListService {
    List<BidList> getBidLists();

    Optional<BidList> getBidListById(Integer bidListId);

    BidList addBidList(BidList bidList);

    BidList updateBidList(BidList bidList);

    String deleteBidList(Integer bidListId);
}
