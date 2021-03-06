package com.gbce.stock.s3Market.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gbce.stock.s3Market.dao.StockMarketDAO;
import com.gbce.stock.s3Market.enums.BuySellIndicator;
import com.gbce.stock.s3Market.model.StockDetails;
import com.gbce.stock.s3Market.service.StockMarketService;

@Service ("stockMarketService")
public class StockMarketServiceImpl implements StockMarketService{

	@Autowired
	StockMarketDAO stockMarketDAO;

	/**
	 * @author Kirti
	 * Calculate Dividend Yield
	 * 
	 * @param StockDetails stockDetails
	 * @return String
	 *
	 */
	public String getDividendYield(StockDetails stockDetails){

		return stockMarketDAO.getDividendYield(stockDetails);
	}


	/**
	 * @author Kirti
	 * Calculate P/E Ratio
	 * 
	 * @param StockDetails stockDetails
	 * @return String
	 *
	 */
	public String getPERatio(StockDetails stockDetails) {
		return stockMarketDAO.getPERatio(stockDetails);
	}



	/**
	 * @author Kirti
	 * Record Data in File, Change the input data and check the records in File
	 *  
	 * @param Date tradeTime, String stockSymbol, Integer quantity, BuySellIndicator buySellIndictor,Double price
	 * @return Map<Date, StockDetails>
	 *
	 */
	public  Map<Date, StockDetails> recordTrade(Date tradeTime, String stockSymbol, Integer quantity, BuySellIndicator buySellIndictor,Double price) {

		return stockMarketDAO.recordTrade(tradeTime, stockSymbol, quantity, buySellIndictor, price);		
	}


	/**
	 * @author Kirti
	 * Volume Weighted Stock Price for last 15 minutes
	 *  
	 * @param Calendar now
	 * @return String
	 *
	 */
	public String getVWSPrice(Calendar now) {
		return stockMarketDAO.getVWSPrice(now);	
	}

	/**
	 * @author Kirti
	 * GBCE All Share Index
	 *  
	 * @param Map<Date, StockDetails> recordedTrade
	 * @return String
	 *
	 */
	public  String getASIndex(Map<Date, StockDetails> recordedTrade) {

		return stockMarketDAO.getASIndex(recordedTrade);
	}	

}
