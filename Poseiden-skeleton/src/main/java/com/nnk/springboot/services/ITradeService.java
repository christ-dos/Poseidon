package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;

import java.util.List;

public interface ITradeService {
    List<Trade> getTrades();

    Trade getTradeById(Integer tradeId);

    Trade addTrade(Trade trade);

    Trade updateTrade(Trade trade);

    String deleteTrade(Integer tradeId);
}
