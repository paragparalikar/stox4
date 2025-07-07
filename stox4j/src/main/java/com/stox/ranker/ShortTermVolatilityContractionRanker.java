package com.stox.ranker;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.ATRIndicator;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.helpers.CombineIndicator;
import org.ta4j.core.indicators.helpers.HighPriceIndicator;
import org.ta4j.core.indicators.helpers.HighestValueIndicator;
import org.ta4j.core.indicators.helpers.LowPriceIndicator;
import org.ta4j.core.num.Num;

import com.stox.common.ui.ConfigView;
import com.stox.common.ui.form.auto.AutoForm;
import com.stox.indicator.PVolatilityIndicator;
import com.stox.ranker.ShortTermVolatilityContractionRanker.ShortTermVolatilityContractionRankerConfig;

import lombok.Data;

import javax.smartcardio.ATR;


// Short Term Volatility Ranker
public class ShortTermVolatilityContractionRanker implements Ranker<ShortTermVolatilityContractionRankerConfig> {

	@Data
	public static class ShortTermVolatilityContractionRankerConfig implements RankerConfig {
		private int barCount = 34;
	}

	@Override
	public String toString() {
		return "Short Term Volatility Contraction";
	}
	
	@Override
	public ShortTermVolatilityContractionRankerConfig createConfig() {
		return new ShortTermVolatilityContractionRankerConfig();
	}

	@Override
	public ConfigView createConfigView(ShortTermVolatilityContractionRankerConfig config) {
		return new AutoForm(config);
	}

	@Override
	public Indicator<Num> createIndicator(ShortTermVolatilityContractionRankerConfig config, BarSeries barSeries) {
		final ATRIndicator atrIndicator = new ATRIndicator(barSeries, config.getBarCount());
		final HighestValueIndicator highestATRIndicator = new HighestValueIndicator(atrIndicator, config.getBarCount());
		final Indicator<Num>  atrRatioIndicator = new CombineIndicator(atrIndicator, highestATRIndicator, Num::dividedBy);
		final Indicator<Num> spreadRatioIndicator = getSpreadRatioIndicator(config, barSeries);
		return new CombineIndicator(atrRatioIndicator, spreadRatioIndicator, Num::multipliedBy);
	}

	private static Indicator<Num> getSpreadRatioIndicator(ShortTermVolatilityContractionRankerConfig config, BarSeries barSeries) {
		final HighPriceIndicator highPriceIndicator = new HighPriceIndicator(barSeries);
		final LowPriceIndicator lowPriceIndicator = new LowPriceIndicator(barSeries);
		final Indicator<Num> spreadIndicator = new CombineIndicator(highPriceIndicator, lowPriceIndicator, Num::minus);
		final HighestValueIndicator highestSpreadIndicator = new HighestValueIndicator(spreadIndicator, config.getBarCount());
        return new CombineIndicator(spreadIndicator, highestSpreadIndicator, Num::dividedBy);
	}

}
