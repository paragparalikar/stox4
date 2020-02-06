package com.stox.persistence.store;

import java.nio.file.Files;
import java.nio.file.Path;

import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;

public class TextFileStore implements Store<String>  {

	@Getter
	private final Path path;
	
	@SneakyThrows
	public TextFileStore(@NonNull final Path path) {
		this.path = path;
		Files.createDirectories(path);
	}

	@Override
	@SneakyThrows
	public String read() {
		return new String(Files.readAllBytes(path));
	}

	@Override
	@SneakyThrows
	public String write(@NonNull final String data) {
		Files.write(path, data.getBytes());
		return data;
	}

}
