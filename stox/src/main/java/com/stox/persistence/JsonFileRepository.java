package com.stox.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.stox.util.JsonConverter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.SneakyThrows;

@Builder
@AllArgsConstructor
@SuppressWarnings("unchecked")
public class JsonFileRepository<I, T> implements Repository<I, T> {

	@NonNull
	private final Path home;

	@NonNull
	private final String name;

	@NonNull
	private final Function<T, I> idRetreivalFunction;

	@NonNull
	private final BiConsumer<I, T> idSetterConsumer;

	@NonNull
	private final Supplier<I> nextIdSupplier;

	@NonNull
	private final JsonConverter jsonConverter;

	private Path getPath() {
		return home.resolve(Paths.get("data", name));
	}

	private Path getPath(I id) {
		return getPath().resolve(Paths.get(String.join(".", String.valueOf(id), "json")));
	}

	private InputStream getInputStreamById(I id) throws IOException {
		return Files.newInputStream(getPath(id), StandardOpenOption.READ);
	}
	
	private OutputStream getOutputStreamById(I id) throws IOException {
		return Files.newOutputStream(getPath(id), StandardOpenOption.WRITE, 
				StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
	}

	@Override
	@SneakyThrows
	public T find(@NonNull final I id) {
		try(final InputStream inputStream = getInputStreamById(id)){
			return (T) jsonConverter.read(inputStream);
		}
	}

	@Override
	@SneakyThrows
	public List<T> findAll() {
		final Path path = getPath();
		if (Files.exists(path)) {
			return (List<T>) Files.list(path).map(filePath -> {
				try (final InputStream inputStream = Files.newInputStream(filePath, StandardOpenOption.READ)){
					return jsonConverter.read(inputStream);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	@Override
	@SneakyThrows
	public I save(@NonNull final T entity) {
		if(Objects.nonNull(idRetreivalFunction.apply(entity))) {
			throw new IllegalArgumentException("Id must be null to save an entity");
		}
		final I id = nextIdSupplier.get();
		idSetterConsumer.accept(id, entity);
		try(final OutputStream outputStream = getOutputStreamById(id)){
			jsonConverter.write(entity, outputStream);
			return id;
		}
	}

	@Override
	@SneakyThrows
	public void update(@NonNull final T entity) {
		final I id = idRetreivalFunction.apply(entity);
		Objects.requireNonNull(id, "Id must NOT be null to update an entity");
		try(final OutputStream outputStream = getOutputStreamById(id)){
			jsonConverter.write(entity, outputStream);
		}
	}

	@Override
	@SneakyThrows
	public void delete(@NonNull final I id) {
		Files.deleteIfExists(getPath(id));
	}

}
