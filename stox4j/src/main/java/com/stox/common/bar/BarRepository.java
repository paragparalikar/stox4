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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Repository;
import org.ta4j.core.Bar;
import org.ta4j.core.BaseBar;
import org.ta4j.core.num.DoubleNum;

import com.stox.common.util.MathUtil;

import lombok.SneakyThrows;

@Repository
public class BarRepository {
	private static final int BYTES = 6 * Double.BYTES;

	private final Path path = Paths.get(System.getProperty("user.home"), ".stox4", "bars", "D");
	
	private Path resolvePath(String isin) {
		return path.resolve(isin);
	}

	private long getDate(final long initialDate, final long location) {
		return initialDate + TimeUnit.DAYS.toMillis((location - Long.BYTES) / BYTES);
	}
	
	private long getLocation(final long initialDate, final long date) {
		return Long.BYTES + BYTES * TimeUnit.MILLISECONDS.toDays(date - initialDate);
	}
	
	@SneakyThrows
	public List<Bar> find(String isin, int count, ZonedDateTime offset){
		final Path path = resolvePath(isin);
		synchronized (isin) {
			final List<Bar> bars = new ArrayList<>();
			try (final RandomAccessFile file = new RandomAccessFile(path.toString(), "r")) {
				if (0 == file.length()) {
					return bars;
				} else {
					final long initialDate = file.readLong();
					final long to = offset.toInstant().toEpochMilli();
					final long maxLocation = MathUtil.clip(Long.BYTES, getLocation(initialDate, to) - BYTES, file.length() - BYTES);
					final long minLocation = MathUtil.clip(Long.BYTES, maxLocation - (count + 1) * BYTES, maxLocation);
					for (long location = maxLocation; location >= minLocation; location -= BYTES) {
						file.seek(location);
						final Bar bar = readBar(file, isin, getDate(initialDate, location));
						if (null != bar) bars.add(bar);
					}
					return bars;
				}
			} catch(FileNotFoundException e) {
				return bars;
			}
		}
	}
	
	@SneakyThrows
	public List<Bar> find(String isin, int count) {
		final Path path = resolvePath(isin);
		synchronized (isin) {
			final List<Bar> bars = new ArrayList<>();
			try (final RandomAccessFile file = new RandomAccessFile(path.toString(), "r")) {
				if (0 == file.length()) {
					return bars;
				} else {
					final long initialDate = file.readLong();
					for (long location = file.length() - BYTES; location >= Long.BYTES
							&& bars.size() < count; location -= BYTES) {
						file.seek(location);
						final Bar bar = readBar(file, isin, getDate(initialDate, location));
						if (null != bar) bars.add(bar);
					}
					return bars;
				}
			} catch(FileNotFoundException e) {
				return bars;
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
		return 0 >= open ? null : BaseBar.builder()
			.openPrice(DoubleNum.valueOf(open))
			.highPrice(DoubleNum.valueOf(high))
			.lowPrice(DoubleNum.valueOf(low))
			.closePrice(DoubleNum.valueOf(close))
			.volume(DoubleNum.valueOf(volume))
			.timePeriod(Duration.ofDays(1))
			.endTime(ZonedDateTime.ofInstant(Instant.ofEpochMilli(epochMillis), ZoneId.systemDefault()))
			.build();
	}
}
