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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BidListServiceTest {

    private BidListService bidListServiceTest;

    @Mock
    private BidListRepository bidListRepositoryMock;

    private BidList bidListTest;

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

    @Test
    public void getBidListByIdTest_whenBidListExist_thenReturnBidList() {
        //GIVEN
        when(bidListRepositoryMock.getById(anyInt())).thenReturn(bidListTest);
        //WHEN
        BidList bidListResult = bidListServiceTest.getBidListById(bidListTest.getBidListId());
        //THEN
        verify(bidListRepositoryMock, times(1)).getById(anyInt());
        assertNotNull(bidListResult);
        assertEquals("Account Test", bidListResult.getAccount());
        assertEquals(10d, bidListResult.getBidQuantity());
    }

    @Test
    public void getBidListByIdTest_whenBidListNotExist_thenThrowBidListNotFoundException() {
        //GIVEN
        when(bidListRepositoryMock.getById(isA(Integer.class))).thenReturn(null);
        //WHEN
        //THEN
        verify(bidListRepositoryMock, times(0)).getById(isA(Integer.class));
        assertThrows(BidListNotFoundException.class, () -> bidListServiceTest.getBidListById(bidListTest.getBidListId()));
    }

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

        when(bidListRepositoryMock.getById(isA(Integer.class))).thenReturn(bidListTest);
        when(bidListRepositoryMock.save(isA(BidList.class))).thenReturn(bidListTestUpdated);
        //WHEN
        BidList bidListUpdated = bidListServiceTest.updateBidList(bidListTestUpdated);
        //THEN
        verify(bidListRepositoryMock, times(1)).save(isA(BidList.class));
        assertEquals("Account Test Updated", bidListUpdated.getAccount());
        assertEquals(20d, bidListUpdated.getBidQuantity());
        assertTrue(bidListTestUpdated.getRevisionDate().after(Timestamp.valueOf(dateRevisionIsAfter)));
    }

    @Test
    public void updateBidListTest_whenBidListNotExist_thenThrowBidListNotFoundException() {
        //GIVEN
        when(bidListRepositoryMock.getById(anyInt())).thenReturn(null);
        //WHEN
        //THEN
        verify(bidListRepositoryMock, times(0)).save(isA(BidList.class));
        assertThrows(BidListNotFoundException.class, () -> bidListServiceTest.updateBidList(bidListTest));
    }

    @Test
    public void deleteBidListTest_whenBidListExist_ThenReturnMessageBideListDeleted() {
        //GIVEN
        Integer id = 1;
        //WHEN
        String messageResult = bidListServiceTest.deleteBidList(id);
        //THEN
        verify(bidListRepositoryMock,times(1)).deleteById(isA(Integer.class));
        assertEquals("BidList deleted", messageResult);
    }

}
