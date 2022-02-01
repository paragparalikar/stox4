package com.stox.client.kite.adapter;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.stox.client.kite.KiteClient;
import com.stox.client.kite.model.Candle;
import com.stox.client.kite.model.CandleSeries;
import com.stox.client.kite.util.KiteUtil;
import com.stox.common.bar.Bar;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class KiteDeltaBarDownloader {

	private final KiteClient kiteClient;
	
	@SneakyThrows
	public List<Bar> download(int instrumentToken, String interval, ZonedDateTime limit){
		final List<Bar> allBars = new LinkedList<>();
		final Duration increment = KiteUtil.getMaxIncrement(interval);
		final Duration duration = KiteUtil.toInterval(interval).getDuration();
		ZonedDateTime to = ZonedDateTime.now();
		ZonedDateTime from = computeFrom(to, limit, increment);
		
		while(true) {
			if(0 <= from.compareTo(to)) break;
			log.info("Attempting to download data from {} to {}", from, to);
			final CandleSeries series = kiteClient.getData(instrumentToken, interval, from, to);
			log.info("Successfully downloaded {} candles", series.getData().size());
			final ZonedDateTime from_ = from;
			final List<Bar> bars = series.getData().stream()
					.filter(candle -> 0 < candle.getTimestamp().compareTo(from_))
					.map(candle -> transform(candle, duration))
					.collect(Collectors.toList());
			log.info("{} bars found to be eligilbe", bars.size());
			if(bars.isEmpty()) break;
			allBars.addAll(0, bars);
			to = bars.get(0).beginTime();
			from = computeFrom(to, limit, increment);
			Thread.sleep(300);
		}
		return allBars;
	}
	
	private ZonedDateTime computeFrom(ZonedDateTime to, ZonedDateTime limit, Duration increment) {
		final ZonedDateTime from = to.minus(increment);
		return 0 < from.compareTo(limit) ? from : limit;
	}
	
	private Bar transform(Candle candle, Duration duration) {
		return Bar.builder()
				.open(candle.getOpen())
				.high(candle.getHigh())
				.low(candle.getLow())
				.close(candle.getClose())
				.volume(candle.getVolume())
				.date(candle.getTimestamp().toInstant().toEpochMilli())
				.timePeriod(duration)
				.build();
	}
	
}
