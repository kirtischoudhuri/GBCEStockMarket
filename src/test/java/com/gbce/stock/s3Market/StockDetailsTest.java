package com.gbce.stock.s3Market;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

import com.gbce.stock.s3Market.dao.impl.StockMarketDAOImpl;
import com.gbce.stock.s3Market.enums.BuySellIndictor;
import com.gbce.stock.s3Market.enums.StockType;
import com.gbce.stock.s3Market.model.StockDetails;

public class StockDetailsTest {

	StockMarketDAOImpl stockMarketDAOImpl = new StockMarketDAOImpl();
	@Test
	public void testDividend() {
		// Test Dividend Yield for Common
		StockDetails stockTEA = new StockDetails("POP",StockType.COMMON,8.0,0.0,100.0,6.0);
		Double dividendTEA = Double.parseDouble(stockMarketDAOImpl.getDividendYield(stockTEA));
		assertEquals(1.33, dividendTEA, 0.0);
		// Test Dividend Yield for Preferred
		StockDetails stockGIN = new StockDetails("GIN",StockType.PREFERED,8.0,0.2,100.0,8.0);
		Double dividendGIN = Double.parseDouble(stockMarketDAOImpl.getDividendYield(stockGIN));
		assertEquals(2.5, dividendGIN, 0.0);

	}

	@Test
	public void testPERatio() {
		StockDetails stock = new StockDetails("POP",StockType.COMMON,8.0,0.0,100.0,6.0);
		Double peRatio = Double.parseDouble(stockMarketDAOImpl.getPERatio(stock));
		assertEquals(4.51, peRatio, 0.0);
	}


	@Test
	public void testFailurePERatio() {
		StockDetails stock = new StockDetails("POP",StockType.COMMON,8.0,0.0,100.0,6.0);
		Double peRatio = Double.parseDouble(stockMarketDAOImpl.getPERatio(stock));
		Assert.assertNotSame(6.00, peRatio);
	}

	@Test
	public void testVWSPrice() {
		stockMarketDAOImpl.recordTrade(Calendar.getInstance().getTime(),"ALE",5,BuySellIndictor.SELL,7.0);
		stockMarketDAOImpl.recordTrade(Calendar.getInstance().getTime(),"POP",6,BuySellIndictor.BUY,6.0);
		stockMarketDAOImpl.recordTrade(Calendar.getInstance().getTime(),"JOE",7,BuySellIndictor.SELL,5.0);

		Double vWSPrice = Double.parseDouble(stockMarketDAOImpl.getVWSPrice(Calendar.getInstance()));
		assertEquals(5.89, vWSPrice, 0.0);
	}
	@Test
	public void testGetASIndex() {

		stockMarketDAOImpl.recordTrade(Calendar.getInstance().getTime(),"ALE",5,BuySellIndictor.SELL,7.0);
		stockMarketDAOImpl.recordTrade(Calendar.getInstance().getTime(),"POP",6,BuySellIndictor.BUY,6.0);
		stockMarketDAOImpl.recordTrade(Calendar.getInstance().getTime(),"JOE",7,BuySellIndictor.SELL,5.0);

		Double gbceASIndex = Double.parseDouble(stockMarketDAOImpl.getASIndex(StockMarketDAOImpl.tradeData));

		assertEquals(5.94, gbceASIndex, 0.0);
	}
}
