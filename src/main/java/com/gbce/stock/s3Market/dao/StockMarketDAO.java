package com.gbce.stock.s3Market.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.gbce.stock.s3Market.enums.BuySellIndictor;
import com.gbce.stock.s3Market.model.StockDetails;

public interface StockMarketDAO {

	/**
	 * @author Kirti
	 * Calculate Dividend Yield
	 * 
	 * @param StockDetails stockDetails
	 * @return String
	 *
	 */
	public String getDividendYield(StockDetails stockDetails);

	/**
	 * @author Kirti
	 * Calculate P/E Ratio
	 * 
	 * @param StockDetails stockDetails
	 * @return String
	 *
	 */
	public String getPERatio(StockDetails stockDetails) ;


	/**
	 * @author Kirti
	 * Record Data in File, Change the input data and check the records in File
	 *  
	 * @param Date tradeTime, String stockSymbol, Integer quantity, BuySellIndictor buySellIndictor,Double price
	 * @return Map<Date, StockDetails>
	 *
	 */
	public  Map<Date, StockDetails> recordTrade(Date tradeTime, String stockSymbol, Integer quantity, BuySellIndictor buySellIndictor,Double price);

	/**
	 * @author Kirti
	 * Volume Weighted Stock Price for last 15 minutes
	 *  
	 * @param Calendar now
	 * @return String
	 *
	 */
	public String getVWSPrice(Calendar now);

	/**
	 * @author Kirti
	 * GBCE All Share Index
	 *  
	 * @param Map<Date, StockDetails> recordedTrade
	 * @return String
	 *
	 */
	public  String getASIndex(Map<Date, StockDetails> recordedTrade);
}
