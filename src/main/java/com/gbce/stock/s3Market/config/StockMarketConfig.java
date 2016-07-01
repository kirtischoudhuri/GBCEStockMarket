package com.gbce.stock.s3Market.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gbce.stock.s3Market.dao.StockMarketData;
import com.gbce.stock.s3Market.dao.impl.StockMarketDataImpl;
import com.gbce.stock.s3Market.model.StockDetails;

@Configuration
public class StockMarketConfig {

	@Bean(name="stockDetails")
	public StockDetails stockDetails() {
		return new StockDetails();
	}

	@Bean(name="stockMarketData")
	public  StockMarketData stockData() {
		return new StockMarketDataImpl();
	}
}
