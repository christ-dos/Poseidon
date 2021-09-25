package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

/**
 * Class that test {@link TradeRepository}
 *
 * @author Christine Duarte
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeTests {

	/**
	 * An Instance of TradeRepository
	 */
	@Autowired
	private TradeRepository tradeRepository;

	/**
	 * Method that test save, update, findAll and delete a {@link Trade}
	 */
	@Test
	public void tradeTest() {
		Trade trade = Trade.builder()
				.account("Trade Account")
				.type("Type")
				.buyQuantity(10d)
				.build();
		// Save
		trade = tradeRepository.save(trade);
		Assert.assertNotNull(trade.getTradeId());
		Assert.assertTrue(trade.getAccount().equals("Trade Account"));

		// Update
		trade.setAccount("Trade Account Update");
		trade = tradeRepository.save(trade);
		Assert.assertTrue(trade.getAccount().equals("Trade Account Update"));

		// Find
		List<Trade> listResult = tradeRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = trade.getTradeId();
		tradeRepository.delete(trade);
		Optional<Trade> tradeList = tradeRepository.findById(id);
		Assert.assertFalse(tradeList.isPresent());
	}
}
