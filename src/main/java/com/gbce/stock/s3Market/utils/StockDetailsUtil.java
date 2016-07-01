package com.gbce.stock.s3Market.utils;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import com.gbce.stock.s3Market.constant.StockMarketConstant;
import com.gbce.stock.s3Market.model.StockDetails;

public class StockDetailsUtil {



	/**
	 * @author Kirti
	 * Sample data Stocks traded in last 15 minutes can be received via various ways like Webservice,DB,File etc. I am storing the data in HashMap.
	 * 
	 * @param 
	 * @return HashMap<String, StockDetails>
	 *
	 */
	public static Map<Date, StockDetails> getLast15MinTrades(Calendar now, Map<Date, StockDetails> tradeData) {
		Map<Date, StockDetails> last15MinTradeData = new TreeMap<>();
		now.add(Calendar.MINUTE, -(StockMarketConstant.REQ_PAST_MINUTES));
		
		for(Map.Entry<Date, StockDetails> entry : tradeData.entrySet()) {
			  Date key = entry.getKey();
				  if (key.after(now.getTime())){
					  last15MinTradeData.put(key, entry.getValue());
				  }

		}

		return last15MinTradeData;
	}


	/**
	 * @author Kirti
	 * Round number upto 2 decimal
	 *  
	 * @param Double
	 * @return Double
	 *
	 */
	public static String fomatTo2DecimalPt(Double number) {
		DecimalFormat df = new DecimalFormat("###.##");
		return df.format(number);
	}
	
}
