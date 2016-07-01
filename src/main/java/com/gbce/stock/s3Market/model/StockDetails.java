package com.gbce.stock.s3Market.model;

import java.util.Date;

import com.gbce.stock.s3Market.enums.BuySellIndictor;
import com.gbce.stock.s3Market.enums.StockType;

public class StockDetails {

	private String stockSymbol;
	private StockType type;
	private Double lastDividend;
	private Double fixedDividend;
	private Double parValue;
	private Double price;
	private Integer quantity;
	private Date tradeTime;
	private BuySellIndictor buySellIndictor;


	public String getStockSymbol() {
		return stockSymbol;
	}

	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	public StockType getType() {
		return type;
	}

	public void setType(StockType type) {
		this.type = type;
	}

	public Double getLastDividend() {
		return lastDividend;
	}

	public void setLastDividend(Double lastDividend) {
		this.lastDividend = lastDividend;
	}

	public Double getFixedDividend() {
		return fixedDividend;
	}

	public void setFixedDividend(Double fixedDividend) {
		this.fixedDividend = fixedDividend;
	}

	public Double getParValue() {
		return parValue;
	}

	public void setParValue(Double parValue) {
		this.parValue = parValue;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Date getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}

	public BuySellIndictor getBuySellIndictor() {
		return buySellIndictor;
	}

	public void setBuySellIndictor(BuySellIndictor buySellIndictor) {
		this.buySellIndictor = buySellIndictor;
	}
	public StockDetails() {
	}

	public StockDetails(String symbol, StockType type, Double lastDividend, Double fixedDividend, Double parValue,Double price) {
		this.setStockSymbol(symbol);
		this.setType(type);
		this.setLastDividend(lastDividend);
		this.setFixedDividend(fixedDividend);
		this.setParValue(parValue);
		this.setPrice(price);
	}

	public StockDetails(Date tradeTime,String symbol, Integer quantity, BuySellIndictor buySellIndictor, Double price) {
		this.setTradeTime(tradeTime);
		this.setStockSymbol(symbol);
		this.setQuantity(quantity);
		this.setBuySellIndictor(buySellIndictor);
		this.setPrice(price);
	}
	
}
