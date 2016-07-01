package com.gbce.stock.s3Market;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.gbce.stock.s3Market.utils.StockDetailsUtil;

public class StockDetailsUtilTest {


	@Test
	public void testFomatTo2DecimalPt() {
		Double decimal = 6.789064; 
		Double formattedString = Double.parseDouble(StockDetailsUtil.fomatTo2DecimalPt(decimal));
		assertEquals(6.79, formattedString, 0.0);
	}
}


