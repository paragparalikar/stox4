package com.stox.core.persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.stox.core.model.Bar;
import com.stox.core.model.BarSpan;
import com.stox.core.model.intf.BarProvider;
import com.stox.util.Uncheck;

import lombok.NonNull;
import lombok.SneakyThrows;

public class BarRepository implements BarProvider {
	private static final int BYTES = 6 * Double.BYTES;

	private final Path home;
	
	private final Map<String, RandomAccessFile> fileCache = new LinkedHashMap<String, RandomAccessFile>() {
		private static final long serialVersionUID = 1L;

		protected boolean removeEldestEntry(Map.Entry<String, RandomAccessFile> eldest) {
			if (size() > 10) {
				Optional.ofNullable(eldest.getValue()).ifPresent(Uncheck.consumer(RandomAccessFile::close));
				return true;
			}
			return false;
		}
	};

	public BarRepository(@NonNull final Path home) {
		this.home = home;
		try {
			Runtime.getRuntime().addShutdownHook(new Thread(() -> fileCache.values().forEach(Uncheck.consumer(RandomAccessFile::close))));
			for (final BarSpan barSpan : BarSpan.values()) {
				Files.createDirectories(Paths.get(getPath("", barSpan)));
			}
		} catch (final IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private RandomAccessFile getFile(String path) throws IOException {
		final RandomAccessFile file = fileCache.computeIfAbsent(path, p -> {
			try {
				return new RandomAccessFile(p, "r");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		});
		file.seek(0);
		return file;
	}

	private String getPath(final String isin, final BarSpan barSpan) {
		final BarSpan effectiveBarSpan = barSpan.equals(BarSpan.M) || barSpan.equals(BarSpan.W) ? BarSpan.D : barSpan;
		return home.resolve(Paths.get("bars",effectiveBarSpan.getShortName(), isin)).toString().intern();
	}

	private long getLocation(final long initialDate, final long date) {
		return Long.BYTES + BYTES * TimeUnit.MILLISECONDS.toDays(date - initialDate);
	}

	private long getDate(final long initialDate, final long location) {
		return initialDate + TimeUnit.DAYS.toMillis((location - Long.BYTES) / BYTES);
	}

	@SneakyThrows
	public List<Bar> find(final String isin, final BarSpan barSpan, final long from, final long to) {
		final String path = getPath(isin, barSpan);
		synchronized (path) {
			final RandomAccessFile file = getFile(path);
			if (0 == file.length()) {
				return Collections.emptyList();
			} else {
				final List<Bar> bars = new ArrayList<>();
				final long initialDate = file.readLong();
				final long minLocation = Math.max(getLocation(initialDate, from), 0);
				final long maxLocation = Math.min(getLocation(initialDate, to), file.length() - BYTES);
				for (long location = maxLocation; location >= minLocation; location -= BYTES) {
					file.seek(location);
					final Bar bar = readBar(file);
					if (null != bar) {
						bar.setIsin(isin);
						bar.setDate(getDate(initialDate, location));
						bars.add(bar);
					}
				}
				if (!BarSpan.D.equals(barSpan)) {
					return barSpan.merge(bars);
				}
				return bars;
			}

		}
	}

	@Override
	public List<Bar> get(String isin, BarSpan barSpan, int count) {
		return find(isin, barSpan, count);
	}

	@SneakyThrows
	@SuppressWarnings("incomplete-switch")
	public List<Bar> find(final String isin, final BarSpan barSpan, int count) {
		final String path = getPath(isin, barSpan);
		synchronized (path) {
			final RandomAccessFile file = getFile(path);
			if (0 == file.length()) {
				return Collections.emptyList();
			} else {
				final List<Bar> bars = new ArrayList<>();
				final long initialDate = file.readLong();

				switch (barSpan) {
				case W:
					count *= 7;
					break;
				case M:
					count *= 31;
					break;
				}

				for (long location = file.length() - BYTES; location >= Long.BYTES
						&& bars.size() < count; location -= BYTES) {
					file.seek(location);
					final Bar bar = readBar(file);
					if (null != bar) {
						bar.setIsin(isin);
						bar.setDate(getDate(initialDate, location));
						bars.add(bar);
					}
				}
				if (!BarSpan.D.equals(barSpan)) {
					return barSpan.merge(bars);
				}
				return bars;
			}

		}
	}

	@Override
	public List<Bar> get(String isin, BarSpan barSpan, long from, long to) {
		return find(isin, barSpan, from, to);
	}

	private void write(final Bar bar, final RandomAccessFile file) throws IOException {
		if (0 == file.length()) {
			file.writeLong(bar.getDate());
			writeBar(bar, file);
		} else {
			file.seek(0);
			final long location = getLocation(file.readLong(), bar.getDate());
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
		file.writeDouble(bar.getOpen());
		file.writeDouble(bar.getHigh());
		file.writeDouble(bar.getLow());
		file.writeDouble(bar.getClose());
		file.writeDouble(bar.getPreviousClose());
		file.writeDouble(bar.getVolume());
	}

	private Bar readBar(final RandomAccessFile file) throws IOException {
		final Bar bar = new Bar();
		bar.setOpen(file.readDouble());
		bar.setHigh(file.readDouble());
		bar.setLow(file.readDouble());
		bar.setClose(file.readDouble());
		bar.setPreviousClose(file.readDouble());
		bar.setVolume(file.readDouble());
		return 0 >= bar.getOpen() || 0 >= bar.getHigh() || 0 >= bar.getLow() || 0 >= bar.getClose()
				|| 0 >= bar.getVolume() ? null : bar;
	}

	public void drop(final String isin, final BarSpan barSpan) {
		final String path = getPath(isin, barSpan);
		synchronized (path) {
			final File file = new File(path);
			if (file.exists()) {
				file.delete();
			}
		}
	}

	public long getLastTradingDate(final String isin, final BarSpan barSpan) {
		final String path = getPath(isin, barSpan);
		synchronized (path) {
			try (final RandomAccessFile file = new RandomAccessFile(path, "rw")) {
				if (file.length() >= Long.BYTES) {
					return file.length() + file.readLong() - 6 * Double.BYTES;
				}
			} catch (Exception e) {
			}
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.YEAR, -9);
			return calendar.getTimeInMillis();
		}
	}

	public void save(final String isin, final BarSpan barSpan, final List<Bar> bars) {
		final String path = getPath(isin, barSpan);
		synchronized (path) {
			try (final RandomAccessFile file = new RandomAccessFile(path, "rw")) {
				for (final Bar bar : bars) {
					write(bar, file);
				}
			} catch (final IOException ioException) {
				throw new RuntimeException(ioException);
			}
		}
	}

	public void save(final Bar bar, final BarSpan barSpan) {
		final String path = getPath(bar.getIsin(), barSpan);
		synchronized (path) {
			try (final RandomAccessFile file = new RandomAccessFile(path, "rw")) {
				write(bar, file);
			} catch (final IOException ioException) {
				throw new RuntimeException(ioException);
			}
		}
	}

}
