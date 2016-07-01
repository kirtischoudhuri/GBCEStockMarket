package com.gbce.stock.s3Market.dao.impl;

import java.util.HashMap;

import com.gbce.stock.s3Market.dao.StockMarketData;
import com.gbce.stock.s3Market.enums.StockType;
import com.gbce.stock.s3Market.model.StockDetails;

public class StockMarketDataImpl implements StockMarketData {
	/**
	 * @author Kirti
	 * Sample data along with price can be received and stored via various ways like GUI,DB,File etc. I am storing the data in HashMap.
	 * 
	 * @param 
	 * @return HashMap<String, StockDetails>
	 *
	 */
	public HashMap<String, StockDetails> getStockData() {
		
		HashMap<String, StockDetails> stockData = new HashMap<>();
		stockData.put("TEA",new StockDetails("TEA",StockType.COMMON,0.0,0.0,100.0,5.0));
		stockData.put("POP",new StockDetails("POP",StockType.COMMON,8.0,0.0,100.0,6.0));
		stockData.put("ALE",new StockDetails("ALE",StockType.COMMON,23.0,0.0,60.0,7.0));
		stockData.put("GIN",new StockDetails("GIN",StockType.PREFERED,8.0,0.2,100.0,8.0));
		stockData.put("JOE",new StockDetails("JOE",StockType.COMMON,13.0,0.0,250.0,9.0));
		
		return stockData;
	}

}
