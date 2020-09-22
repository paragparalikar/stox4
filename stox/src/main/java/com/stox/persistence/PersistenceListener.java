package com.stox.persistence;

import static java.nio.file.Files.deleteIfExists;
import static java.nio.file.Files.write;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.stox.util.collection.listenable.Listener;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class PersistenceListener<E> implements Listener<E> {
	
	@NonNull private final Path path;
	@NonNull private final Function<E, String> formatter;

	@Override
	@SneakyThrows
	public void clear() {
		deleteIfExists(path);
	}

	@Override
	@SneakyThrows
	public void add(E item) {
		Files.createDirectories(path.getParent());
		final Set<String> lines = Optional.of(item)
			.map(formatter)
			.map(Collections::singleton)
			.get();
		write(path, lines, CREATE, WRITE, APPEND);
	}

	@Override
	@SneakyThrows
	public void retainAll(Collection<E> items) {
		Files.createDirectories(path.getParent());
		final List<String> lines = items.stream().map(formatter).collect(Collectors.toList());
		write(path, lines, CREATE, WRITE, TRUNCATE_EXISTING);
	}

}
