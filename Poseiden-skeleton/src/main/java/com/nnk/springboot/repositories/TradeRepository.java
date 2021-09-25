package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface that handles database queries for trade
 *
 * @author Christine Duarte
 */
public interface TradeRepository extends JpaRepository<Trade, Integer> {
}
