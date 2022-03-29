package com.stox.common;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class SerializationRepository {

	private final Path home;
	
	private Path getPath(String key) {
		return home.resolve(Paths.get("objects", key));
	}
	
	@SneakyThrows
	public void serialize(@NonNull Object object) {
		final Path path = getPath(object.getClass().getCanonicalName());
		Files.createDirectories(path.getParent());
		try(ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path))){
			oos.writeObject(object);
		}
	}
	
	@SneakyThrows
	@SuppressWarnings("unchecked")
	public <T> T deserialize(@NonNull Class<T> type) {
		final Path path = getPath(type.getCanonicalName());
		if(!Files.exists(path)) return null;
		try(ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path))){
			return (T) ois.readObject();
		}
	}
	
}
