package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exceptions.TradeNotFoundException;

import java.util.List;

/**
 * An Interface for {@link TradeService}
 *
 * @author Christine Duarte
 */
public interface ITradeService {
    /**
     * Method that get all {@link Trade}
     *
     * @return A list of {@link Trade}
     */
    List<Trade> getTrades();

    /**
     * Method that get a {@link Trade} by Id
     *
     * @param tradeId An Integer containing the id of the Trade
     * @return An instance of {@link Trade}
     */
    Trade getTradeById(Integer tradeId) throws TradeNotFoundException;

    /**
     * Method which add a {@link Trade}
     *
     * @param trade An instance {@link Trade}
     * @return The {@link Trade} saved
     */
    Trade addTrade(Trade trade);

    /**
     * Method which update a {@link Trade}
     *
     * @param trade An instance {@link Trade}
     * @return the {@link Trade} updated
     */
    Trade updateTrade(Trade trade);

    /**
     * Method that delete a {@link Trade}
     *
     * @param tradeId An integer containing the id
     * @return A String containing "Trade deleted"
     */
    String deleteTrade(Integer tradeId);
}
