package com.gbce.stock.s3Market;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.gbce.stock.s3Market.controller.StockMarketController;
import com.gbce.stock.s3Market.dao.StockMarketData;
import com.gbce.stock.s3Market.enums.BuySellIndicator;
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
	private static BuySellIndicator buySellIndicater;
	
	private static Log log = LogFactory.getLog(StockMarketMain.class);
	public static void main( String[] args )
	{

		try{
			Map<String, StockDetails> stockData =new HashMap<>();
			String tradeStock= "";
			int tradeQuantity = 0;
			double tradePrice = 0.0;

			context = new AnnotationConfigApplicationContext(StockMarketMain.class);
			StockMarketController stockMarketController = (StockMarketController) context.getBean("stockMarketController");

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
						buySellIndicater=BuySellIndicator.valueOf(sBuySellIndicater);
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


				stockMarketController.executeTrade(stockData, tradeStock, tradeQuantity, tradePrice,
						buySellIndicater);

			}
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	
}