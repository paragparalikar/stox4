package com.stox.persistence;

import static java.nio.file.Files.deleteIfExists;
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
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.stox.util.collection.listenable.Listener;
import com.stox.util.function.ThrowingFunction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.Synchronized;

@Builder
@AllArgsConstructor
public class PersistenceListener<E> implements Listener<E> {
	
	@NonNull private final Path path;
	@NonNull private final Collection<E> delegate;
	@NonNull private final Function<E, String> formatter;
	@NonNull private final BiFunction<Integer, String, E> parser;

	@Builder.Default private boolean read = false;
	
	@Override
	@SneakyThrows
	@Synchronized
	public void truncate() {
		deleteIfExists(path);
	}

	@Override
	@SneakyThrows
	@Synchronized
	public void append(final E item) {
		Files.createDirectories(path.getParent());
		final Set<String> lines = Optional.of(item)
			.map(formatter)
			.map(Collections::singleton)
			.get();
		Files.write(path, lines, CREATE, WRITE, APPEND);
	}

	@Override
	@SneakyThrows
	@Synchronized
	public void rewrite() {
		Files.createDirectories(path.getParent());
		final List<String> lines = delegate.stream().map(formatter).collect(Collectors.toList());
		Files.write(path, lines, CREATE, WRITE, TRUNCATE_EXISTING);
	}

	@Override
	@SneakyThrows
	@Synchronized
	public void read() {
		if(!read) {
			final List<String> lines = Optional.ofNullable(path)
				.filter(Files::exists)
				.map(ThrowingFunction.from(Files::readAllLines))
				.orElse(Collections.emptyList());
			IntStream.range(0, lines.size())
				.mapToObj(index -> parser.apply(index, lines.get(index)))
				.forEach(delegate::add);
			read = true;
		}
	}

}
