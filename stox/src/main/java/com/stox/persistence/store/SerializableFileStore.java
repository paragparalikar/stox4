package com.stox.persistence.store;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class SerializableFileStore<T> implements Store<T> {

	@NonNull
	private final Path path;

	@Override
	@SneakyThrows
	@SuppressWarnings("unchecked")
	public T read() {
		return Files.exists(path) ? (T) new ObjectInputStream(Files.newInputStream(path)).readObject() : null;
	}

	@Override
	@SneakyThrows
	public T write(T data) {
		new ObjectOutputStream(Files.newOutputStream(path)).writeObject(data);
		return data;
	}

}
