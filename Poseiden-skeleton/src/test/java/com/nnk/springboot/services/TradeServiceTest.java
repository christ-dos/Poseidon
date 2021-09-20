package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exceptions.TradeNotFoundException;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Class that test {@link TradeService}
 *
 * @author Christine Duarte
 */
@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {
    /**
     * An Instance of TradeService
     */
    private TradeService tradeServiceTest;

    /**
     * A mock of TradeRepository
     */
    @Mock
    private TradeRepository tradeRepositoryMock;

    /**
     * An attribute of Trade
     */
    private Trade tradeTest;

    /**
     * Method that initialize instances to perform each test
     */
    @BeforeEach
    public void setPerTest() {
        tradeServiceTest = new TradeService(tradeRepositoryMock);
        tradeTest = Trade.builder()
                .tradeId(1)
                .account("Account")
                .type("Type")
                .buyQuantity(10d)
                .build();
    }

    /**
     * Method that test get all {@link Trade}
     * then return a list with three elements
     */
    @Test
    public void getTradesTest_whenListOfTradeContainThreeElements_thenReturnSizeIsGreaterThanZero() {
        //GIVEN
        List<Trade> trades = new ArrayList<>(
                Arrays.asList(
                        Trade.builder().tradeId(1).account("account").type("type").buyQuantity(10d).build(),
                        Trade.builder().tradeId(2).account("account1").type("type1").buyQuantity(20d).build(),
                        Trade.builder().tradeId(3).account("account2").type("type2").buyQuantity(30d).build()));
        when(tradeRepositoryMock.findAll()).thenReturn(trades);
        //WHEN
        List<Trade> tradesResult = tradeServiceTest.getTrades();
        //THEN
        verify(tradeRepositoryMock, times(1)).findAll();
        assertEquals(trades, tradesResult);
        assertTrue(tradesResult.size() > 0);
    }

    /**
     * Method that test get trade by id
     * when trade is found in database
     */
    @Test
    public void getTradeByIdTest_whenTradeIsFound_thenReturnTrade() {
        //GIVEN
        when(tradeRepositoryMock.findById(isA(Integer.class))).thenReturn(java.util.Optional.ofNullable(tradeTest));
        //WHEN
        Trade tradeByIdResult = tradeServiceTest.getTradeById(tradeTest.getTradeId());
        //THEN
        verify(tradeRepositoryMock, times(1)).findById(isA(Integer.class));
        assertNotNull(tradeByIdResult);
        assertEquals(1, tradeByIdResult.getTradeId());
        assertEquals("Account", tradeByIdResult.getAccount());
        assertEquals("Type", tradeByIdResult.getType());
        assertEquals(10d, tradeByIdResult.getBuyQuantity());
    }

    /**
     * Method that test get trade by id
     * when trade not found in database
     * then return a {@link TradeNotFoundException}
     */
    @Test
    public void getTradeByIdTest_whenTradeNotFound_thenTradeNotFoundException() {
        //GIVEN
        when(tradeRepositoryMock.findById(isA(Integer.class))).thenReturn(Optional.empty());
        //WHEN
        //THEN
        verify(tradeRepositoryMock, times(0)).findById(isA(Integer.class));
        assertThrows(TradeNotFoundException.class, () -> tradeServiceTest.getTradeById(tradeTest.getTradeId()));
    }

    /**
     * Method that test add a trade
     * when trade is not recorded in database
     */
    @Test
    public void addTradeTest_whenTradeNotRecordedInDb_thenReturnTradeAdded() {
        //GIVEN
        when(tradeRepositoryMock.save(isA(Trade.class))).thenReturn(tradeTest);
        //WHEN
        Trade tradeResult = tradeServiceTest.addTrade(tradeTest);
        //THEN
        verify(tradeRepositoryMock, times(1)).save(isA(Trade.class));
        assertEquals(1, tradeResult.getTradeId());
        assertEquals("Account", tradeResult.getAccount());
        assertEquals("Type", tradeResult.getType());
        assertEquals(10d, tradeResult.getBuyQuantity());
    }

    /**
     * Method that test update a trade
     * when trade exist in database
     */
    @Test
    public void updateTradeTest_whenTradeExist_thenReturnTradeUpdated() {
        //GIVEN
        Trade tradeTestUpdated = Trade.builder()
                .tradeId(1)
                .account("Account updated")
                .type("Type updated")
                .buyQuantity(30d)
                .build();

        when(tradeRepositoryMock.getById(isA(Integer.class))).thenReturn(tradeTest);
        when(tradeRepositoryMock.save(isA(Trade.class))).thenReturn(tradeTestUpdated);
        //WHEN
        Trade tradeUpdatedResult = tradeServiceTest.updateTrade(tradeTestUpdated);
        //THEN
        verify(tradeRepositoryMock, times(1)).save(isA(Trade.class));
        assertEquals("Account updated", tradeUpdatedResult.getAccount());
        assertEquals("Type updated", tradeUpdatedResult.getType());
        assertEquals(30d, tradeUpdatedResult.getBuyQuantity());
    }

    /**
     * Method that test update a trade
     * when trade not exist in database
     * then throw {@link TradeNotFoundException}
     */
    @Test
    public void updateTradeTest_whenTradeNotExist_thenThrowTradeNotFoundException() {
        //GIVEN
        when(tradeRepositoryMock.getById(anyInt())).thenReturn(null);
        //WHEN
        //THEN
        verify(tradeRepositoryMock, times(0)).save(isA(Trade.class));
        assertThrows(TradeNotFoundException.class, () -> tradeServiceTest.updateTrade(tradeTest));
    }

    /**
     * Method that test deletion by id of a trade
     */
    @Test
    public void deleteTradeTest_whenTradeExist_ThenReturnMessageTradeDeleted() {
        //GIVEN
        Integer id = 1;
        //WHEN
        String messageResult = tradeServiceTest.deleteTrade(id);
        //THEN
        verify(tradeRepositoryMock, times(1)).deleteById(anyInt());
        assertEquals("Trade deleted", messageResult);
    }
}
