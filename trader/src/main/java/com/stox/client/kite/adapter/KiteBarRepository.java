package com.stox.client.kite.adapter;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.stox.client.kite.util.KiteUtil;
import com.stox.common.bar.Bar;

import lombok.SneakyThrows;

public class KiteBarRepository {
	private static final int BYTES = Double.BYTES * 6 + Long.BYTES * 2;
	
	private final Path path = Paths.get(System.getProperty("user.home"), ".stox4", "bars");

	private Path getPath(int instrumentToken, String interval) {
		return path.resolve(Paths.get(String.valueOf(instrumentToken), interval));
	}

	@SneakyThrows
	public void write(Bar bar, DataOutput out) {
		out.writeDouble(bar.open());
		out.writeDouble(bar.high());
		out.writeDouble(bar.low());
		out.writeDouble(bar.close());
		out.writeDouble(bar.volume());
		out.writeDouble(bar.amount());
		out.writeLong(bar.trades());
		out.writeLong(bar.date());
	}

	@SneakyThrows
	public Bar read(String interval, DataInput in) {
		return Bar.builder()
				.open(in.readDouble())
				.high(in.readDouble())
				.low(in.readDouble())
				.close(in.readDouble())
				.volume(in.readDouble())
				.amount(in.readDouble())
				.trades(in.readLong())
				.date(in.readLong())
				.timePeriod(KiteUtil.toInterval(interval).getDuration())
				.build();
	}

	@SneakyThrows
	public void write(int instrumentToken, String interval, List<Bar> bars) {
		final Path path = getPath(instrumentToken, interval);
		Files.createDirectories(path.getParent());
		try(RandomAccessFile file = new RandomAccessFile(path.toFile(), "rw")){
			file.seek(file.length());
			bars.forEach(bar -> write(bar, file));
		}
	}
	
	@SneakyThrows
	public boolean exists(int instrumentToken, String interval) {
		final Path path = getPath(instrumentToken, interval);
		return Files.exists(path) && BYTES < Files.size(path);
	}
	
	@SneakyThrows
	public ZonedDateTime getLastBarEndTime(int instrumentToken, String interval) {
		if(exists(instrumentToken, interval)) {
			final Path path = getPath(instrumentToken, interval);
			try(RandomAccessFile file = new RandomAccessFile(path.toFile(), "r")){
				file.seek(file.length() - BYTES);
				final Bar bar = read(interval, file);
				return bar.getEndTime();
			}
		}
		return null;
	}
	
	@SneakyThrows
	public List<Bar> read(int instrumentToken, String interval){
		final List<Bar> bars = new ArrayList<>();
		if (exists(instrumentToken, interval)) {
			final Path path = getPath(instrumentToken, interval);
			try(RandomAccessFile file = new RandomAccessFile(path.toFile(), "r")){
				file.seek(0);
				while(BYTES <= (file.length() - file.getFilePointer())) {
					bars.add(read(interval, file));
				}
			}
		}
		return bars;
	}
	
	public List<Bar> read(String instrumentToken, String interval, int count){
		return null;
	}
	
	public List<Bar> read(String instrumentToken, String interval, long from, long to){
		return null;
	}
}
