package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exceptions.BidListNotFoundException;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

/**
 * Class that test {@link BidListService}
 *
 * @author Christine Duarte
 */
@ExtendWith(MockitoExtension.class)
public class BidListServiceTest {
    /**
     * An Instance of BidListService
     */
    private BidListService bidListServiceTest;

    /**
     * A mock of BidListRepository
     */
    @Mock
    private BidListRepository bidListRepositoryMock;

    /**
     * An attribute of BidList
     */
    private BidList bidListTest;

    /**
     * Method that initialize instances to perform each test
     */
    @BeforeEach
    public void setPerTest() {
        bidListServiceTest = new BidListService(bidListRepositoryMock);

        bidListTest = BidList.builder()
                .bidListId(1)
                .creationDate(Timestamp.valueOf(LocalDateTime.now()))
                .account("Account Test")
                .bidQuantity(10d)
                .type("Type Test")
                .build();
    }

    /**
     * Method that test get all {@link BidList}
     * then return a list with three elements
     */
    @Test
    public void getBidListsTest_whenListOfBidContainThreeElements_thenReturnSizeIsGreaterThanZero() {
        //GIVEN
        List<BidList> bidLists = new ArrayList<>(
                Arrays.asList(
                        BidList.builder().account("Account Test").bidQuantity(10d).type("Type Test").build(),
                        BidList.builder().account("Account Test1").bidQuantity(20d).type("Type Test1").build(),
                        BidList.builder().account("Account Test2").bidQuantity(30d).type("Type Test2").build()));
        when(bidListRepositoryMock.findAll()).thenReturn(bidLists);
        //WHEN
        List<BidList> bidListsResult = bidListServiceTest.getBidLists();
        //THEN
        verify(bidListRepositoryMock, times(1)).findAll();
        assertEquals(bidLists, bidListsResult);
        assertTrue(bidListsResult.size() > 0);
    }

    /**
     * Method that test get bidList by id
     * when bidList is found in database
     */
    @Test
    public void getBidListByIdTest_whenBidListIsFound_thenReturnBidList() {
        //GIVEN
        when(bidListRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(bidListTest));
        //WHEN
        BidList bidListResult = bidListServiceTest.getBidListById(bidListTest.getBidListId());
        //THEN
        verify(bidListRepositoryMock, times(1)).findById(anyInt());
        assertNotNull(bidListResult);
        assertEquals("Account Test", bidListResult.getAccount());
        assertEquals(10d, bidListResult.getBidQuantity());
    }

    /**
     * Method that test get bidList by id
     * when bidList not found in database
     * then return a {@link BidListNotFoundException}
     */
    @Test
    public void getBidListByIdTest_whenBidListNotFound_thenThrowBidListNotFoundException() {
        //GIVEN
        when(bidListRepositoryMock.findById(isA(Integer.class))).thenReturn(Optional.empty());
        //WHEN
        //THEN
        verify(bidListRepositoryMock, times(0)).findById(isA(Integer.class));
        assertThrows(BidListNotFoundException.class, () -> bidListServiceTest.getBidListById(bidListTest.getBidListId()));
    }

    /**
     * Method that test add a bidList
     * when bidList is not recorded in database
     */
    @Test
    public void addBidListTest_whenBidListNotRecordedInDb_thenReturnBidListAdded() {
        //GIVEN
        when(bidListRepositoryMock.save(isA(BidList.class))).thenReturn(bidListTest);
        //WHEN
        BidList bidListResult = bidListServiceTest.addBidList(bidListTest);
        //THEN
        verify(bidListRepositoryMock, times(1)).save(isA(BidList.class));
        assertEquals("Account Test", bidListResult.getAccount());
        assertEquals("Type Test", bidListResult.getType());
        assertEquals(10d, bidListResult.getBidQuantity());
        assertNotNull(bidListTest.getCreationDate());
    }

    /**
     * Method that test update a bidList
     * when bidList exist in database
     */
    @Test
    public void updateBidListTest_whenBidListExist_thenReturnBidListUpdated() {
        //GIVEN
        BidList bidListTestUpdated = BidList.builder()
                .bidListId(1)
                .account("Account Test Updated")
                .bidQuantity(20d)
                .type("Type Test")
                .revisionDate(Timestamp.from(Instant.now()))
                .build();
        LocalDateTime dateRevisionIsAfter = LocalDateTime.of(2021, 9, 10, 14, 00);

        when(bidListRepositoryMock.getById(1)).thenReturn(bidListTest);
        when(bidListRepositoryMock.save(isA(BidList.class))).thenReturn(bidListTestUpdated);
        //WHEN
        BidList bidListUpdated = bidListServiceTest.updateBidList(bidListTestUpdated);
        //THEN
        verify(bidListRepositoryMock, times(1)).save(isA(BidList.class));
        assertEquals("Account Test Updated", bidListUpdated.getAccount());
        assertEquals(20d, bidListUpdated.getBidQuantity());
        assertTrue(bidListTestUpdated.getRevisionDate().after(Timestamp.valueOf(dateRevisionIsAfter)));
    }

    /**
     * Method that test update a bidList
     * when bidList not exist in database
     * then throw {@link BidListNotFoundException}
     */
    @Test
    public void updateBidListTest_whenBidListNotExist_thenThrowBidListNotFoundException() {
        //GIVEN
        when(bidListRepositoryMock.getById(anyInt())).thenReturn(null);
        //WHEN
        //THEN
        verify(bidListRepositoryMock, times(0)).save(isA(BidList.class));
        assertThrows(BidListNotFoundException.class, () -> bidListServiceTest.updateBidList(bidListTest));
    }

    /**
     * Method that test deletion by id of a bidList
     */
    @Test
    public void deleteBidListTest_whenBidListExist_ThenReturnMessageBideListDeleted() {
        //GIVEN
        Integer id = 1;
        //WHEN
        String messageResult = bidListServiceTest.deleteBidList(id);
        //THEN
        verify(bidListRepositoryMock, times(1)).deleteById(isA(Integer.class));
        assertEquals("BidList deleted", messageResult);
    }
}
