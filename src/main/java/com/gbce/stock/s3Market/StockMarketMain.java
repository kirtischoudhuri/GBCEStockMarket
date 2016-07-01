package com.gbce.stock.s3Market;

import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.gbce.stock.s3Market.controller.StockMarketController;
import com.gbce.stock.s3Market.dao.StockMarketData;
import com.gbce.stock.s3Market.enums.BuySellIndictor;
import com.gbce.stock.s3Market.enums.StockType;
import com.gbce.stock.s3Market.model.StockDetails;
import com.gbce.stock.s3Market.validate.StockMarketValidator;
import com.gbce.stock.s3Market.validate.StockMarketValidator.StockInformation;

/**
 * Super Simple Stock Market
 *
 */
@ComponentScan
public class StockMarketMain 
{

	private static ApplicationContext context;
	private static Scanner scanner;
	private static BuySellIndictor buySellIndicater;
	
	public static void main( String[] args )
	{
		Log log = LogFactory.getLog(StockMarketMain.class);

		try{
			Double price = 0.0;
			String stockSymbol="";
			StockDetails stock = null;
			Map<String, StockDetails> stockData =new HashMap<>();
			Map<Date, StockDetails> recordedTrades =new HashMap<>();
			StockType type;
			String dividentYeild = "";
			String peRatio = "";
			String vwsPrice="";
			String gbceASIndex = "";
			String tradeStock= "";
			int tradeQuantity = 0;
			double tradePrice = 0.0;

			context = new AnnotationConfigApplicationContext(StockMarketMain.class);
			StockMarketController stockMarketController = (StockMarketController) context.getBean("stockMarketController");
			StockDetails stockDetails = context.getBean("stockDetails", StockDetails.class);

			//Fetch data for the given Stock
			StockMarketData	stockMarketData = context.getBean("stockMarketData", StockMarketData.class);
			stockData = stockMarketData.getStockData();
			Set<String> keySet = stockData.keySet();


			 
			scanner = new Scanner(new InputStreamReader(System.in));

			while(true){
				
				//Displaying the Test Stocks Present  
				log.debug("Please enter one of the below Test Stock");
				Iterator<String> i = keySet.iterator();
				while(i.hasNext()){
					log.debug("Stock Name : "+i.next());
				}
				
				//For a given Stock
				
				boolean indicator = false;
				while(!indicator){
					log.debug("Enter one of the above Stock you want to trade");
					tradeStock = scanner.nextLine().toUpperCase().trim();
					indicator = StockMarketValidator.validateData(StockInformation.STOCK, tradeStock)&&StockMarketValidator.validateStock(stockData, tradeStock);
					if(!indicator){
						log.debug("Please enter valid value");
					}
				}

				indicator = false;
				while(!indicator){
					log.debug("Do you want to Buy/Sell");
					String sBuySellIndicater = scanner.nextLine().toUpperCase().trim();
					indicator = StockMarketValidator.validateData(StockInformation.BUYSELL, sBuySellIndicater);
					if(indicator){
						buySellIndicater=BuySellIndictor.valueOf(sBuySellIndicater);
					}else{
						log.debug("Please enter valid value");
					}
				}

				indicator = false;
				while(!indicator){
					log.debug("Enter quantity for transaction");
					String sTradeQuantity =  scanner.nextLine();
					indicator = StockMarketValidator.validateData(StockInformation.TRADEQUANTITY, sTradeQuantity);
					if(indicator){
						tradeQuantity=Integer.parseInt(sTradeQuantity);
					}else{
						log.debug("Please enter valid value");
					}
				}

				indicator = false;
				while(!indicator){
					log.debug("Enter "+buySellIndicater+" price");
					String sTradePrice =  scanner.nextLine();
					indicator = StockMarketValidator.validateData(StockInformation.TRADEPRICE, sTradePrice);
					if(indicator){
						tradePrice=Double.parseDouble(sTradePrice);
					}else{
						log.debug("Please enter valid value");
					}
				}


				Calendar now = Calendar.getInstance();
				stockDetails = new StockDetails(now.getTime(),tradeStock,tradeQuantity,buySellIndicater,tradePrice);

				stock = stockData.get(tradeStock.toUpperCase());
				BeanUtils.copyProperties(stock,stockDetails);
				type = stockDetails.getType();

				// Calculate Dividend Yield
				dividentYeild = stockMarketController.getDividendYield(stockDetails);
				//Need to handle if DividendYield=Infinity is not acceptable 
				if (dividentYeild.toString() == "Infinity"){
					log.debug("Dividend Yeild for Stock :"+tradeStock+" of Price : "+tradePrice+" and Type: "+type+" is : "+dividentYeild+" (may be needed to handle)");
				}else{
					log.debug("Dividend Yeild for Stock :"+tradeStock+" of Price : "+tradePrice+" and Type: "+type+" is : "+dividentYeild);
				}

				// Calculate P/E Ratio
				peRatio = stockMarketController.getPERatio(stockDetails);
				//Need to handle if P/E Ratio = Infinity is not acceptable
				log.debug("P/E Ratio for Stock :"+stockSymbol+" of Price : "+price+" and Type: "+type+" is : "+peRatio);

				//As soon as Trading is done call method recordTrade
				//Record Trade in File
				log.debug("Recorded Trades Till Now : ");
				 recordedTrades = stockMarketController.recordTrade(now.getTime(),tradeStock,tradeQuantity, buySellIndicater, tradePrice);
				log.debug("Data recorded in File : TradeDataRecord.txt");

				// Calculate vwsPrice
				vwsPrice = stockMarketController.getVWSPrice(now);
				log.debug("Volume Weighted Stock Price for last 15 minutes is  : "+vwsPrice);

				// Calculate GBCE All Share Index         
				gbceASIndex = stockMarketController.getASIndex(recordedTrades);
				log.debug("GBCE All Share Index is : "+gbceASIndex);

			}
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}