package com.gbce.stock.s3Market.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.gbce.stock.s3Market.enums.BuySellIndicator;
import com.gbce.stock.s3Market.enums.StockType;
import com.gbce.stock.s3Market.model.StockDetails;
import com.gbce.stock.s3Market.service.StockMarketService;

@Controller ("stockMarketController")
public class StockMarketController {

	@Autowired
	StockMarketService stockMarketService;
	
	private static Log log = LogFactory.getLog(StockMarketController.class);

	public void executeTrade( Map<String, StockDetails> stockData,
			String tradeStock, int tradeQuantity, double tradePrice, BuySellIndicator buySellIndicater) {
		StockDetails stock;
		Map<Date, StockDetails> recordedTrades;
		StockType type;
		String dividentYeild;
		String peRatio;
		String vwsPrice;
		String gbceASIndex;
		StockDetails stockDetails;
		Calendar now = Calendar.getInstance();
		stockDetails = new StockDetails(now.getTime(),tradeStock,tradeQuantity,buySellIndicater,tradePrice);

		stock = stockData.get(tradeStock.toUpperCase());
		BeanUtils.copyProperties(stock,stockDetails);
		type = stockDetails.getType();

		// Calculate Dividend Yield
		dividentYeild = stockMarketService.getDividendYield(stockDetails);
		//Need to handle if DividendYield=Infinity is not acceptable 
		if (dividentYeild.toString() == "Infinity"){
			log.debug("Dividend Yeild for Stock :"+tradeStock+" of Price : "+tradePrice+" and Type: "+type+" is : "+dividentYeild+" (may be needed to handle)");
		}else{
			log.debug("Dividend Yeild for Stock :"+tradeStock+" of Price : "+tradePrice+" and Type: "+type+" is : "+dividentYeild);
		}

		// Calculate P/E Ratio
		peRatio = stockMarketService.getPERatio(stockDetails);
		//Need to handle if P/E Ratio = Infinity is not acceptable
		log.debug("P/E Ratio for Stock :"+tradeStock+" of Price : "+tradePrice+" and Type: "+type+" is : "+peRatio);

		//As soon as Trading is done call method recordTrade
		//Record Trade in File
		log.debug("Recorded Trades Till Now : ");
		recordedTrades = stockMarketService.recordTrade(now.getTime(),tradeStock,tradeQuantity, buySellIndicater, tradePrice);
		log.debug("Data recorded in File : TradeDataRecord.txt");

		// Calculate vwsPrice
		vwsPrice = stockMarketService.getVWSPrice(now);
		log.debug("Volume Weighted Stock Price for last 15 minutes is  : "+vwsPrice);

		// Calculate GBCE All Share Index         
		gbceASIndex = stockMarketService.getASIndex(recordedTrades);
		log.debug("GBCE All Share Index is : "+gbceASIndex);
	}



}
