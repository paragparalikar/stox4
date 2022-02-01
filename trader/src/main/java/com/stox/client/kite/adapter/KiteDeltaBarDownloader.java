package com.stox.client.kite.adapter;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.stox.client.kite.KiteClient;
import com.stox.client.kite.model.Candle;
import com.stox.client.kite.model.CandleSeries;
import com.stox.client.kite.util.KiteUtil;
import com.stox.common.bar.Bar;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class KiteDeltaBarDownloader {

	private final KiteClient kiteClient;
	private final KiteBarRepository kiteBarRepository;
	
	@SneakyThrows
	public List<Bar> download(int instrumentToken, String interval){
		final List<Bar> allBars = new ArrayList<>();
		final Duration increment = KiteUtil.getMaxIncrement(interval);
		final Duration duration = KiteUtil.toInterval(interval).getDuration();
		final ZonedDateTime limit = Optional.ofNullable(kiteBarRepository.getLastBarEndTime(instrumentToken, interval))
				.orElseGet(() -> ZonedDateTime.now().minus(Duration.ofDays(4000)));
		ZonedDateTime to = ZonedDateTime.now();
		ZonedDateTime from = computeFrom(to, limit, increment);
		
		while(true) {
			final CandleSeries series = kiteClient.getData(instrumentToken, interval, from, to);
			if(series.getData().isEmpty()) {
				break;
			} else {
				final List<Bar> bars = series.getData().stream()
						.map(candle -> transform(candle, duration))
						.collect(Collectors.toList());
				kiteBarRepository.write(instrumentToken, interval, bars);
				allBars.addAll(bars);
				to = bars.get(0).beginTime();
				from = computeFrom(to, limit, increment);
				Thread.sleep(300);
			}
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
