package com.stox.persistence;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.stox.persistence.store.JsonFileStore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.SneakyThrows;

@Builder
@AllArgsConstructor
public class JsonFileRepository<I, T> implements Repository<I, T> {

	@NonNull
	private final Path path;

	@NonNull
	private final Class<T> type;

	@NonNull
	private final Gson gson;

	@NonNull
	private final Function<T, I> idRetreivalFunction;

	@NonNull
	private final BiConsumer<I, T> idSetterConsumer;

	@NonNull
	private final Supplier<I> nextIdSupplier;

	private Path buildPath(@NonNull final I id) {
		return path.resolve(Paths.get(String.join(".", String.valueOf(id), "json")));
	}

	@Override
	public T find(@NonNull final I id) {
		final Path path = buildPath(id);
		return Files.exists(path) ? new JsonFileStore<>(path, type, gson).read() : null;
	}

	@Override
	@SneakyThrows
	public List<T> findAll() {
		return Files.list(path).map(filePath -> new JsonFileStore<>(filePath, type, gson).read()).collect(Collectors.toList());
	}

	@Override
	public I save(@NonNull final T entity) {
		if (Objects.nonNull(idRetreivalFunction.apply(entity))) {
			throw new IllegalArgumentException("Id must be null to save an entity");
		}
		final I id = nextIdSupplier.get();
		idSetterConsumer.accept(id, entity);
		new JsonFileStore<>(buildPath(id), type, gson).write(entity);
		return id;
	}

	@Override
	public void update(@NonNull final T entity) {
		final I id = idRetreivalFunction.apply(entity);
		if (Objects.isNull(id)) {
			throw new IllegalArgumentException("Id must NOT be null to update an entity");
		}
		new JsonFileStore<>(buildPath(id), type, gson).write(entity);
	}

	@Override
	@SneakyThrows
	public void delete(@NonNull final I id) {
		Files.deleteIfExists(buildPath(id));
	}

}
