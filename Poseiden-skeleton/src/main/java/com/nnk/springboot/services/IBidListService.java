package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exceptions.BidListNotFoundException;

import java.util.List;

/**
 * An Interface for {@link BidListService}
 *
 * @author Christine Duarte
 */
public interface IBidListService {
    /**
     * Method that get all {@link BidList}
     *
     * @return A list of {@link BidList}
     */
    List<BidList> getBidLists();

    /**
     * Method that get a {@link BidList} by Id
     *
     * @param bidListId An Integer containing the id of the BidList
     * @return An Optional of {@link BidList}
     */
    BidList getBidListById(Integer bidListId) throws BidListNotFoundException;

    /**
     * Method which add a {@link BidList}
     *
     * @param bidList An instance {@link BidList}
     * @return The {@link BidList} saved
     */
    BidList addBidList(BidList bidList);

    /**
     * Method which update a {@link BidList}
     *
     * @param bidList An instance {@link BidList}
     * @return the {@link BidList} updated
     */
    BidList updateBidList(BidList bidList);

    /**
     * Method that delete a {@link BidList}
     *
     * @param bidListId An integer containing the id
     * @return A String containing "BidList deleted"
     */
    String deleteBidList(Integer bidListId);
}
