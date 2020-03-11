package com.stox.persistence.store;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import com.stox.util.Uncheck;

import lombok.NonNull;
import lombok.SneakyThrows;

public class TextFileStore implements Store<String>  {

	private final Path path;
	
	@SneakyThrows
	public TextFileStore(@NonNull final Path path) {
		this.path = path;
		Optional.ofNullable(path.getParent()).ifPresent(Uncheck.consumer(Files::createDirectories));
	}

	@Override
	@SneakyThrows
	public String read() {
		return Files.exists(path) ? new String(Files.readAllBytes(path)) : null;
	}

	@Override
	@SneakyThrows
	public String write(@NonNull final String data) {
		Files.write(path, data.getBytes());
		return data;
	}

}
