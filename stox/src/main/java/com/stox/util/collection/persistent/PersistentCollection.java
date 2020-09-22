package com.stox.util.collection.persistent;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import com.stox.util.collection.lazy.LazyCollection;
import com.stox.util.function.ThrowingFunction;

import lombok.NonNull;
import lombok.SneakyThrows;

public class PersistentCollection<E> extends LazyCollection<E> {
	
	private final Path path;
	private final Function<String, E> parser;

	public PersistentCollection(
			@NonNull final Path path,
			@NonNull final Function<String, E> parser,
			@NonNull final Supplier<? extends Collection<E>> collectionSupplier) {
		super(collectionSupplier);
		this.path = path;
		this.parser = parser;
		
		
		
	}
	
	@Override
	@SneakyThrows
	protected Collection<E> delegate() {
		final Collection<E> delegate = super.delegate();
		Optional.of(path)
			.filter(Files::exists)
			.map(ThrowingFunction.from(Files::readAllLines))
			.orElse(Collections.emptyList())
			.stream()
			.map(parser)
			.forEach(delegate::add);
		return delegate;
	}
	
}
