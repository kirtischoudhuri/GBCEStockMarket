package com.gbce.stock.s3Market.validate;

import java.util.Map;

import com.gbce.stock.s3Market.enums.BuySellIndictor;
import com.gbce.stock.s3Market.model.StockDetails;

public class StockMarketValidator {
	
	public enum StockInformation {
		STOCK,BUYSELL,TRADEQUANTITY,TRADEPRICE
	}
	
	public static boolean validateData(StockInformation validateFor, String data){
		if(validateFor.equals(StockInformation.STOCK)){
			return (data!=null&&data.trim().length()!=0)?true:false;
		}else if(validateFor.equals(StockInformation.BUYSELL)){
			try{
				BuySellIndictor.valueOf(data.toUpperCase());
				return true;
			}catch(Exception e){
				return false;
			}
		}else if(validateFor.equals(StockInformation.TRADEQUANTITY)){
			return isPositiveInteger(data);
		}else if(validateFor.equals(StockInformation.TRADEPRICE)){
			return isDouble(data);
		}
		
		return true;
	}
	
	public static boolean validateStock(Map<String, StockDetails> stockData, String stock){
		return stockData.containsKey(stock);
	}
	
	public static boolean isDouble(String data){
		try
		{
		  Double value = Double.parseDouble(data);
		  if(value<=0){
	        	return false;
	        }
		}
		catch(Exception e) {
	        return false;
	    }
		return true;
	}
	
	public static boolean isPositiveInteger(String data) {
	    try { 
	        Integer value = Integer.parseInt(data); 
	        if(value<=0){
	        	return false;
	        }
	    } catch(Exception e) { 
	        return false; 
	    } 
	    return true;
	}
}
