package com.stox.persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Supplier;

import lombok.Builder;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.Value;

@Value
@Builder
public class IntegerFileSequence implements Supplier<Integer> {

	@NonNull
	private final Path home;
	
	@NonNull
	private final String name;
	
	@Override
	@SneakyThrows
	public Integer get() {
		final Path path = home.resolve(Paths.get(name, "sequence.txt"));
		if(Files.exists(path)) {
			return write(path, 1 + read(path));
		}else {
			Files.createDirectories(path.getParent());
			return write(path, 1);
		}
	}
	
	private Integer read(final Path path) throws NumberFormatException, IOException {
		return Integer.parseInt(new String(Files.readAllBytes(path)));
	}
	
	private Integer write(final Path path, final Integer value) throws IOException {
		Files.write(path, value.toString().getBytes());
		return value;
	}

}
