package com.stox.common.bar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.ta4j.core.Bar;
import org.ta4j.core.BaseBar;
import org.ta4j.core.num.DoubleNum;

import com.stox.common.util.Maths;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class BarRepository {
	private static final int BYTES = 6 * Double.BYTES;
	private static final String METADATA = "metadata.properties";
	private static final String KEY_LAST_DOWNLOAD_DATE = "last-download-date";

	private final Path home;
	private final Properties properties = new Properties();
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	
	private Path resolvePath(String isin) {
		return home.resolve(Paths.get("bars", "D", isin));
	}
	
	@SneakyThrows
	public void writeLastDownloadDate(ZonedDateTime date) {
		final Path path = resolvePath(METADATA);
		Files.createDirectories(path.getParent());
		if(properties.isEmpty() && Files.exists(path)) {
			properties.load(Files.newInputStream(path));
		}
		properties.setProperty(KEY_LAST_DOWNLOAD_DATE, formatter.format(date));
		properties.store(Files.newOutputStream(path), "");
	}
	
	@SneakyThrows
	public ZonedDateTime readLastDownloadDate() {
		final Path path = resolvePath(METADATA);
		if(properties.isEmpty() && Files.exists(path)) {
			properties.load(Files.newInputStream(path));
		}
		final String text = properties.getProperty(KEY_LAST_DOWNLOAD_DATE, "20000101");
		final LocalDate localDate = LocalDate.from(formatter.parse(text));
		return localDate.atStartOfDay(ZoneId.systemDefault());
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
					final long maxLocation = Maths.clip(Long.BYTES, getLocation(initialDate, to) - BYTES, file.length() - BYTES);
					if(maxLocation <= Long.BYTES) return bars;
					for(long location = maxLocation; location >= Long.BYTES && bars.size() < count; location-= BYTES) {
						file.seek(location);
						final Bar bar = readBar(file, isin, getDate(initialDate, location));
						if (null != bar) bars.add(bar);
					}
					Collections.reverse(bars);
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
					final long maxLocation = file.length() - BYTES;
					for(long location = maxLocation; location >= Long.BYTES && bars.size() < count; location-= BYTES) {
						file.seek(location);
						final Bar bar = readBar(file, isin, getDate(initialDate, location));
						if (null != bar) bars.add(bar);
					}
					Collections.reverse(bars);
					return bars;
				}
			} catch(FileNotFoundException e) {
				return bars;
			}
		}
	}
	
	@SneakyThrows
	public void save(String isin, Bar bar) {
		final Path path = resolvePath(isin);
		synchronized(isin) {
			try (final RandomAccessFile file = new RandomAccessFile(path.toString(), "rw")) {
				write(bar, file);
			}
		}
	}
	
	@SneakyThrows
	public void save(String isin, Iterable<Bar> bars) {
		final Path path = resolvePath(isin);
		synchronized(isin) {
			try (final RandomAccessFile file = new RandomAccessFile(path.toString(), "rw")) {
				for(Bar bar : bars) write(bar, file);
			}
		}
	}
	
	private void write(final Bar bar, final RandomAccessFile file) throws IOException {
		final long date = bar.getEndTime().toInstant().toEpochMilli();
		if (0 == file.length()) {
			file.writeLong(date);
			writeBar(bar, file);
		} else {
			file.seek(0);
			final long location = getLocation(file.readLong(), date);
			pad(location, file);
			file.seek(location);
			writeBar(bar, file);
		}
	}

	private void pad(final long location, final RandomAccessFile file) throws IOException {
		if (location > file.length()) {
			file.seek(file.length());
			while (location > file.length()) {
				file.writeDouble(0);
			}
		}
	}

	private void writeBar(final Bar bar, final RandomAccessFile file) throws IOException {
		file.writeDouble(bar.getOpenPrice().doubleValue());
		file.writeDouble(bar.getHighPrice().doubleValue());
		file.writeDouble(bar.getLowPrice().doubleValue());
		file.writeDouble(bar.getClosePrice().doubleValue());
		file.writeDouble(0);
		file.writeDouble(bar.getVolume().doubleValue());
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
