package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exceptions.TradeNotFoundException;

import java.util.List;

public interface ITradeService {
    List<Trade> getTrades();

    Trade getTradeById(Integer tradeId) throws TradeNotFoundException;

    Trade addTrade(Trade trade);

    Trade updateTrade(Trade trade);

    String deleteTrade(Integer tradeId);
}
