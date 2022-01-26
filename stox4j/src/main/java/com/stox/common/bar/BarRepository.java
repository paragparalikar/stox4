package com.stox.common.bar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBar;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.num.DoubleNum;

import lombok.SneakyThrows;

public class BarRepository {
	private static final int BYTES = 6 * Double.BYTES;

	private final Path path = Paths.get(System.getProperty("user.home"), ".stox4", "bars", "D");
	
	private Path resolvePath(String isin) {
		return path.resolve(isin);
	}

	private long getDate(final long initialDate, final long location) {
		return initialDate + TimeUnit.DAYS.toMillis((location - Long.BYTES) / BYTES);
	}
	
	@SneakyThrows
	public BarSeries find(final String isin, int count) {
		final Path path = resolvePath(isin);
		synchronized (isin) {
			final BarSeries barSeries = new BaseBarSeries();
			try (final RandomAccessFile file = new RandomAccessFile(path.toString(), "r")) {
				if (0 == file.length()) {
					return barSeries;
				} else {
					final long initialDate = file.readLong();
					for (long location = file.length() - BYTES; location >= Long.BYTES
							&& barSeries.getBarCount() < count; location -= BYTES) {
						file.seek(location);
						final Bar bar = readBar(file, isin, getDate(initialDate, location));
						if (null != bar) barSeries.addBar(bar);
					}
					return barSeries;
				}
			} catch(FileNotFoundException e) {
				return barSeries;
			}
		}
	}
	
	@SuppressWarnings("unused")
	private Bar readBar(final RandomAccessFile file, String isin, long epochMillis) throws IOException {
		final double open = file.readDouble();
		final double high = file.readDouble();
		final double low = file.readDouble();
		final double close = file.readDouble();
		final double previousClose = file.readDouble();
		final double volume = file.readDouble();
		final ZoneId zoneId = ZoneId.systemDefault();
		final Instant instant = Instant.ofEpochMilli(epochMillis);
		final ZonedDateTime date = ZonedDateTime.ofInstant(instant, zoneId);
		return 0 >= open ? null : BaseBar.builder()
			.openPrice(DoubleNum.valueOf(open))
			.highPrice(DoubleNum.valueOf(high))
			.lowPrice(DoubleNum.valueOf(low))
			.closePrice(DoubleNum.valueOf(close))
			.volume(DoubleNum.valueOf(volume))
			.timePeriod(Duration.ofDays(1))
			.endTime(date)
			.build();
	}
}
