package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BidListService {

    @Autowired
    private BidListRepository bidListRepository;

    public BidList addBidList(BidList bidList){
        return bidListRepository.save(bidList);
    }
}
