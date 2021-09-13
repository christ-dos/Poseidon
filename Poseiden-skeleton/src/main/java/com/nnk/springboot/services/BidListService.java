package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exceptions.BidListNotFoundException;
import com.nnk.springboot.repositories.BidListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

/**
 * Class of service that manage {@link BidList} entity
 *
 * @author Christine Duarte
 */
@Service
@Slf4j
public class BidListService implements IBidListService {
    /**
     * An instance Of {@link BidListRepository}
     */
    private final BidListRepository bidListRepository;

    /**
     * Constructor
     *
     * @param bidListRepository An instanec of {@link BidListRepository}
     */
    @Autowired
    public BidListService(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    /**
     * Method that get all {@link BidList}
     *
     * @return A list of {@link BidList}
     */
    @Override
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
    @Override
    public BidList getBidListById(Integer bidListId) {
        BidList bidList = bidListRepository.getById(bidListId);
        if (bidList.getBidListId() == null) {
            log.error("Service: BidList NOT FOUND with ID: " + bidListId);
            throw new BidListNotFoundException("BidList not found");
        }
        log.info("Service: BidList found with ID: " + bidListId);

        return bidList;
    }

    /**
     * Method which add a {@link BidList}
     *
     * @param bidList An instance {@link BidList}
     * @return The {@link BidList} saved
     */
    @Override
    public BidList addBidList(BidList bidList) {
        bidList.setCreationDate(Timestamp.from(Instant.now()));
        log.info("Service: BidList saved");

        return bidListRepository.save(bidList);
    }

    /**
     * Method which update a {@link BidList}
     *
     * @param bidList An instance {@link BidList}
     * @return the {@link BidList} updated
     */
    @Override
    public BidList updateBidList(BidList bidList) {
        BidList bidListToUpdate = bidListRepository.getById(bidList.getBidListId());
        if (bidListToUpdate == null) {
            log.error("Service: BidList NOT FOUND with ID: " + bidList.getBidListId());
            throw new BidListNotFoundException("BidList not found");
        }
        bidListToUpdate.setAccount(bidList.getAccount());
        bidListToUpdate.setType(bidList.getType());
        bidListToUpdate.setBidQuantity(bidList.getBidQuantity());
        bidListToUpdate.setRevisionDate(Timestamp.from(Instant.now()));
        log.info("Service: BidList updated with ID: " + bidList.getBidListId());

        return bidListRepository.save(bidListToUpdate);
    }

    /**
     * Method that delete a {@link BidList}
     * @param bidListId An integer containing the id
     * @return A String containing "BidList deleted"
     */
    @Override
    public String deleteBidList(Integer bidListId) {
        bidListRepository.deleteById(bidListId);
        log.info("Service: BidList deleted with ID: " + bidListId);

        return "BidList deleted";
    }
}


