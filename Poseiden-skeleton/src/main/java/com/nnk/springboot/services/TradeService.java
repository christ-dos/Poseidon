package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exceptions.TradeNotFoundException;
import com.nnk.springboot.repositories.TradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class of service that manage {@link Trade} entity
 *
 * @author Christine Duarte
 */
@Service
@Slf4j
public class TradeService implements ITradeService {
    /**
     * An instance Of {@link TradeRepository}
     */
    private TradeRepository tradeRepository;

    /**
     * Constructor
     *
     * @param tradeRepository An instance of {@link TradeRepository}
     */
    @Autowired
    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    /**
     * Method that get all {@link Trade}
     *
     * @return A list of {@link Trade}
     */
    @Override
    public List<Trade> getTrades() {
        log.info("Service: displaying Trades");
        return tradeRepository.findAll();
    }

    /**
     * Method that get a {@link Trade} by Id
     *
     * @param tradeId An Integer containing the id of the Trade
     * @return An instance of {@link Trade}
     */
    @Override
    public Trade getTradeById(Integer tradeId) {
        Trade trade = tradeRepository.getById(tradeId);
        if (trade == null) {
            log.error("Service: Trade NOT FOUND with ID: " + tradeId);
            throw new TradeNotFoundException("Trade not found");
        }
        log.info("Service: Trade found with ID: " + tradeId);

        return trade;
    }

    /**
     * Method which add a {@link Trade}
     *
     * @param trade An instance {@link Trade}
     * @return The {@link Trade} saved
     */
    @Override
    public Trade addTrade(Trade trade) {
        log.info("Service: Trade saved");
        return tradeRepository.save(trade);
    }

    /**
     * Method which update a {@link Trade}
     *
     * @param trade An instance {@link Trade}
     * @return the {@link Trade} updated
     */
    @Override
    public Trade updateTrade(Trade trade) {
        Trade tradeToUpdate = tradeRepository.getById(trade.getTradeId());
        if (tradeToUpdate == null) {
            log.error("Service: Trade NOT FOUND with ID: " + trade.getTradeId());
            throw new TradeNotFoundException("Trade not found");
        }
        tradeToUpdate.setAccount(trade.getAccount());
        tradeToUpdate.setType(trade.getType());
        tradeToUpdate.setBuyQuantity(trade.getBuyQuantity());
        log.info("Service: Trade updated with ID: " + trade.getTradeId());

        return tradeRepository.save(tradeToUpdate);
    }

    /**
     * Method that delete a {@link Trade}
     *
     * @param tradeId An integer containing the id
     * @return A String containing "Trade deleted"
     */
    @Override
    public String deleteTrade(Integer tradeId) {
        tradeRepository.deleteById(tradeId);
        log.info("Service: Trade deleted with ID:" + tradeId);

        return "Trade deleted";
    }


}
