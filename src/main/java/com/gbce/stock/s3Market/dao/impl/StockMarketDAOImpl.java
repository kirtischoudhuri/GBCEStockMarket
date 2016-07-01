package com.gbce.stock.s3Market.dao.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.gbce.stock.s3Market.dao.StockMarketDAO;
import com.gbce.stock.s3Market.enums.BuySellIndictor;
import com.gbce.stock.s3Market.model.StockDetails;
import com.gbce.stock.s3Market.persist.RecordInFile;
import com.gbce.stock.s3Market.utils.StockDetailsUtil;

@Repository ("stockMarketDAO")
public class StockMarketDAOImpl implements StockMarketDAO{

	private static Log log = LogFactory.getLog(StockMarketDAOImpl.class);
	public static  Map<Date, StockDetails> tradeData = new TreeMap<>();

	/**
	 * @author Kirti
	 * Calculate Dividend Yield
	 * 
	 * @param StockDetails stockDetails
	 * @return String
	 *
	 */
	public String getDividendYield(StockDetails stockDetails){
		//Need to handle Price = 0 as per functionality
		switch(stockDetails.getType()) {
		case COMMON:
			return StockDetailsUtil.fomatTo2DecimalPt(stockDetails.getLastDividend()/stockDetails.getPrice());
		case PREFERED:
			return StockDetailsUtil.fomatTo2DecimalPt(stockDetails.getFixedDividend()*stockDetails.getParValue()/stockDetails.getPrice());
		default:
			return "";
		}
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
		//Handle dividend = 0 as per functionality
		return StockDetailsUtil.fomatTo2DecimalPt( stockDetails.getPrice()/Double.parseDouble(getDividendYield(stockDetails)));
	}



	/**
	 * @author Kirti
	 * Record Data in File, Change the input data and check the records in File
	 *  
	 * @param Date tradeTime, String stockSymbol, Integer quantity, BuySellIndictor buySellIndictor,Double price
	 * @return Map<Date, StockDetails>
	 *
	 */
	public  Map<Date, StockDetails> recordTrade(Date tradeTime, String stockSymbol, Integer quantity, BuySellIndictor buySellIndictor,Double price) {

		tradeData.put(tradeTime,new StockDetails(tradeTime,stockSymbol,quantity,buySellIndictor,price));

		switch(buySellIndictor) {
		case BUY:
			RecordInFile.tradeDataRecord("At "+tradeTime+" "+quantity+" "+stockSymbol+" Stock(s)brought at price "+price);
			break;
		case SELL:
			RecordInFile.tradeDataRecord("At "+tradeTime+" "+quantity+" "+stockSymbol+" Stock(s)sold at price "+price);
			break;
		default:
			log.debug("No Transaction");
			break;

		}
		BufferedReader br = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader("TradeDataRecord.txt"));
			while ((sCurrentLine = br.readLine()) != null) {
				log.debug(sCurrentLine);


			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {		
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return tradeData;		
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
		Integer totalQuantity = 0;
		Double totalPriceQuantity = 0.0;
		String vwsPrice ="0.0";

		Map<Date, StockDetails> last15MinTrades = StockDetailsUtil.getLast15MinTrades(now,tradeData);

		if(!last15MinTrades.isEmpty()){
		for (StockDetails trade: last15MinTrades.values()) {
			totalQuantity += trade.getQuantity();
			totalPriceQuantity += trade.getPrice() * trade.getQuantity();
		}
			vwsPrice = StockDetailsUtil.fomatTo2DecimalPt(totalPriceQuantity/totalQuantity);
		}
		return vwsPrice;		
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
		Double allShare = 1.0;
		String allShareIndex="0.0";
		if (!recordedTrade.isEmpty()){
		for (StockDetails stock: recordedTrade.values()) {
			allShare*=stock.getPrice();
		}
		allShareIndex = StockDetailsUtil.fomatTo2DecimalPt(Math.pow(allShare,1.0/recordedTrade.size()));
		}
		return allShareIndex;
	}	

}
