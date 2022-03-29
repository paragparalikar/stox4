package com.stox.data.cleaner;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.ta4j.core.Bar;
import org.ta4j.core.BaseBar;
import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.num.Num;

import com.stox.StoxApplicationContext;
import com.stox.common.bar.BarService;
import com.stox.common.scrip.Scrip;
import com.stox.common.scrip.ScripService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DataCleaner {

	private final BarService barService;
	
	public void clean(Scrip scrip, int inspectionBarCount) {
		final Num threshold = DoubleNum.valueOf(1.8);
		final List<Bar> bars = barService.find(scrip.getIsin(), inspectionBarCount);
		for(int index = bars.size() - 1; 
				index > bars.size() - 1 - inspectionBarCount && index > 0; 
				index-- ) {
			final Bar currentBar = bars.get(index);
			final Bar previousBar = bars.get(index - 1);
			final Num ratio = previousBar.getClosePrice().dividedBy(currentBar.getClosePrice());
			if(ratio.isGreaterThan(threshold)) clean(scrip, bars, index, ratio);
		}
	}
	
	private void clean(Scrip scrip, List<Bar> bars, int index, Num ratio) {
		final Num factor = DoubleNum.valueOf(round(ratio.doubleValue()));
		log.info("Found stock split for {} on {} with multiple {}", scrip.getName(), 
				DateTimeFormatter.BASIC_ISO_DATE.format(bars.get(index).getEndTime()), 
				factor.doubleValue());
		final List<Bar> cleanBars = new ArrayList<>(index);
		for(int subIndex = 0; subIndex < index; subIndex++) {
			final Bar bar = bars.get(subIndex);
			final Bar cleanBar = transform(bar, factor);
			cleanBars.add(cleanBar);		
		}
		barService.save(scrip.getIsin(), cleanBars);
	}
	
	private Bar transform(Bar bar, Num factor) {
		return BaseBar.builder()
				.endTime(bar.getEndTime())
				.timePeriod(bar.getTimePeriod())
				.openPrice(bar.getOpenPrice().dividedBy(factor))
				.highPrice(bar.getHighPrice().dividedBy(factor))
				.lowPrice(bar.getLowPrice().dividedBy(factor))
				.closePrice(bar.getClosePrice().dividedBy(factor))
				.volume(bar.getVolume().multipliedBy(factor))
				.build();
	}
	
	private int round(double d) {
	    if (d > 0) return (int) (d + 0.5);
	    else return (int) (d - 0.5);
	}
	
	public static void main(String[] args) throws InterruptedException {
		final StoxApplicationContext context = new StoxApplicationContext();
		final BarService barService = context.getBarService();
		final ScripService scripService = context.getScripService();
		final DataCleaner dataCleaner = new DataCleaner(barService);
		final ExecutorService executor = context.getExecutor();
		for(Scrip scrip : scripService.findAll()) {
			executor.execute(() -> {
				dataCleaner.clean(scrip, Integer.MAX_VALUE);
			});
		}
		executor.shutdown();
		executor.awaitTermination(15, TimeUnit.MINUTES);
	}
	
}
