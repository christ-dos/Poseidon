package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

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
                .account("Account Test")
                .bidQuantity(10d)
                .type("Type Test")
                .build();
    }

    @Test
    public void getBidListsTest() {
        //GIVEN
        List<BidList> bidListList = new ArrayList<>(
                Arrays.asList(bidListTest = BidList.builder().account("Account Test").bidQuantity(10d).type("Type Test").build(),
                        BidList.builder().account("Account Test1").bidQuantity(20d).type("Type Test1").build(),
                        BidList.builder().account("Account Test2").bidQuantity(30d).type("Type Test2").build()));
        when(bidListRepositoryMock.findAll()).thenReturn(bidListList);
        //WHEN
        List<BidList> bidListsResult = bidListServiceTest.getBidLists();
        //THEN
        assertEquals(bidListList, bidListsResult);
        assertTrue(bidListsResult.size() > 0);

    }

    @Test
    public void getBidListByIdTest() {
        //GIVEN
//        BidList bidListTest = BidList.builder()
//                .account("Account Test")
//                .bidQuantity(10d)
//                .type("Type Test")
//                .build();
        when(bidListRepositoryMock.getById(isA(Integer.class))).thenReturn(bidListTest);
        //WHEN
        BidList bidListResult = bidListServiceTest.getBidListById(isA(Integer.class));
        //THEN
        assertNotNull(bidListResult);
        assertEquals("Account Test", bidListResult.getAccount());
        assertEquals(10d, bidListResult.getBidQuantity());

    }

    @Test
    public void AddBidListTest() {
        //GIVEN
//        BidList bidListToAddTest = BidList.builder()
//                .account("Account Test")
//                .bidQuantity(10d)
//                .type("Type Test")
//                .build();
        when(bidListRepositoryMock.save(isA(BidList.class))).thenReturn(bidListTest);

        //WHEN
        BidList bidListResult = bidListServiceTest.addBidList(bidListTest);
        //THEN
        assertEquals("Account Test", bidListResult.getAccount());
        assertEquals("Type Test", bidListResult.getType());
        assertEquals(10d, bidListResult.getBidQuantity());

    }


}
