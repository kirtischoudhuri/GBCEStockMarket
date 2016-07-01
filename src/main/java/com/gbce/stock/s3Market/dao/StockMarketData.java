package com.gbce.stock.s3Market.dao;

import java.util.HashMap;

import com.gbce.stock.s3Market.model.StockDetails;

public interface StockMarketData {

	public	HashMap<String, StockDetails> getStockData();
}