package com.stox.module.watchlist.repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.stox.module.core.model.BarSpan;
import com.stox.module.core.model.Scrip;
import com.stox.module.core.persistence.ScripRepository;
import com.stox.module.watchlist.model.Watchlist;
import com.stox.module.watchlist.model.WatchlistEntry;
import com.stox.persistence.PersistenceListener;
import com.stox.util.DeleteFileVisitor;
import com.stox.util.Strings;
import com.stox.util.collection.lazy.LazyList;
import com.stox.util.collection.listenable.ListenableList;
import com.stox.util.function.ThrowingConsumer;
import com.stox.util.function.ThrowingFunction;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class FileWatchlistRepository implements WatchlistRepository{

	@NonNull
	private final Path home;
	
	@NonNull
	private final ScripRepository scripRepository;
	
	private Path resolve() {
		return home.resolve("watchlists");
	}
	
	private Path resolve(final String name) {
		return resolve().resolve(name);
	}
	
	private Path resolve(final String name, final BarSpan barSpan) {
		return resolve(name).resolve(barSpan.shortName());
	}
	
	@Override
	public Watchlist get(final String name) {
		if(!validate(name)) throw new IllegalArgumentException("Watchlist name must not be null ore empty");
		if(!exists(name)) throw new IllegalArgumentException("Watchlist with this name does not exists");
		return populate(new Watchlist(name));
	}
	
	@Override
	@SneakyThrows
	public Watchlist save(@NonNull final Watchlist watchlist) {
		if(!validate(watchlist.name())) throw new IllegalArgumentException("Watchlist name must not be null ore empty");
		if(exists(watchlist.name())) throw new IllegalArgumentException("Watchlist with same name already exists");
		Files.createDirectories(resolve(watchlist.name()));
		return populate(watchlist);
	}
	
	@Override
	@SneakyThrows
	public Watchlist rename(@NonNull final Watchlist watchlist, @NonNull final String newName) {
		if(!validate(newName)) throw new IllegalArgumentException("Watchlist name must not be null ore empty");
		if(exists(newName)) throw new IllegalArgumentException("Watchlist with new name already exists");
		if(!exists(watchlist.name())) throw new IllegalArgumentException("Watchlist with this name does not exists");
		Files.move(resolve(watchlist.name()), resolve(newName));
		watchlist.name(newName);
		return watchlist;
	}
	
	@Override
	@SneakyThrows
	public void delete(@NonNull final String name) {
		if(!exists(name)) throw new IllegalArgumentException("Watchlist with this name does not exists");
		Files.walkFileTree(resolve(name), new DeleteFileVisitor());
	}
	
	@Override
	@SneakyThrows
	public Watchlist clear(@NonNull final Watchlist watchlist) {
		if(!exists(watchlist.name())) throw new IllegalArgumentException("Watchlist with this name does not exists");
		Files.list(resolve(watchlist.name())).forEach(ThrowingConsumer.from(Files::delete));
		return watchlist;
	}
	
	public boolean validate(final String name) {
		return Objects.nonNull(name) && Strings.hasText(name);
	}
	
	@Override
	@SneakyThrows
	public List<Watchlist> findAll(){
		return Optional.of(resolve())
			.filter(Files::exists)
			.map(ThrowingFunction.from(Files::list))
			.orElse(Stream.empty())
			.map(Path::getFileName)
			.map(Path::toString)
			.map(Watchlist::new)
			.map(this::populate)
			.sorted(Watchlist.COMPARATOR)
			.collect(Collectors.toList());
	}
	
	@Override
	@SneakyThrows
	public boolean exists(@NonNull final String name) {
		return Files.exists(resolve(name));
	}
	
	private Watchlist populate(final Watchlist watchlist) {
		final String name = watchlist.name();
		for(final BarSpan barSpan : BarSpan.values()) {
			final Path path = resolve(name, barSpan);
			final Supplier<List<WatchlistEntry>> supplier = supplier(watchlist, barSpan, path);
			final List<WatchlistEntry> entries = new LazyList<>(supplier);
			watchlist.entries().put(barSpan, entries);
		}
		return watchlist;
	}

	private Supplier<List<WatchlistEntry>> supplier(final Watchlist watchlist, final BarSpan barSpan, final Path path){
		return () -> {
			final List<WatchlistEntry> delegate = new ArrayList<>();
			final ListenableList<WatchlistEntry> list = new ListenableList<>(delegate);
			final PersistenceListener<WatchlistEntry> listener = 
					PersistenceListener.<WatchlistEntry>builder()
						.path(path)
						.delegate(delegate)
						.formatter(this::format)
						.parser((index, text) -> parse(index, text, barSpan, watchlist))
						.build();
			list.addListener(listener);
			return list;
		};
	}
	
	private String format(WatchlistEntry entry) {
		return entry.scrip().isin();
	}
	
	private WatchlistEntry parse(final int index, final String text, 
			final BarSpan barSpan, final Watchlist watchlist) {
		final Scrip scrip = scripRepository.find(text);
		return WatchlistEntry.builder()
				.scrip(scrip)
				.index(index)
				.barSpan(barSpan)
				.watchlist(watchlist)
				.build();
	}
}
