package com.stox.ml;

import com.stox.StoxApplicationContext;
import com.stox.ml.screener.BuyTradeClassificationScreener;
import com.stox.ml.screener.BuyTradeClassificationScreener.BuyTradeClassificationConfig;

public class ScreenerDataGenerator {

	public static void main(String[] args) {
		final StoxApplicationContext context = new StoxApplicationContext();
		final BarSeriesNormalizer barSeriesNormalizer = new BarSeriesNormalizer();
		final BuyTradeClassificationConfig classificationConfig = new BuyTradeClassificationConfig();
		final BuyTradeClassificationScreener classificationScreener = new BuyTradeClassificationScreener();
		
		
	}
	
}
