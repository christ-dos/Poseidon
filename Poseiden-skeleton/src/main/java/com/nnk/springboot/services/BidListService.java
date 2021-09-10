package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class of service that manage {@link BidList} entity
 *
 * @author Christine Duarte
 */
@Service
@Slf4j
public class BidListService {

    private BidListRepository bidListRepository;

    @Autowired
    public BidListService(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    /**
     * Method that get all {@link BidList}
     *
     * @return A list of {@link BidList}
     */
    public List<BidList> getBidLists() {
        log.info("Service: displaying BidLists");
        return bidListRepository.findAll();
    }

    /**
     * Method that get a {@link BidList} by Id
     *
     * @param bidListId An Integer containing the id of the BidList
     * @return An instance of {@link BidList}
     */
    public BidList getBidListById(Integer bidListId) {
        log.info("Service: BidList found with BidListId: " + bidListId);
        return bidListRepository.getById(bidListId);
    }

    /**
     * Method which add a {@link BidList}
     *
     * @param bidList an instance {@link BidList}
     * @return The {@link BidList} saved
     */
    public BidList addBidList(BidList bidList) {
        log.info("Service: BidList saved with account: " + bidList.getAccount());
        return bidListRepository.save(bidList);
    }
}
