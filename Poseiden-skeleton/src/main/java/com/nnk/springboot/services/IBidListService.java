package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exceptions.BidListNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IBidListService {
    List<BidList> getBidLists();

    BidList getBidListById(Integer bidListId) throws BidListNotFoundException;

    BidList addBidList(BidList bidList);

    BidList updateBidList(BidList bidList);

    String deleteBidList(Integer bidListId);
}
