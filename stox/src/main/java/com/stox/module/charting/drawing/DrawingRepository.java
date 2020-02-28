package com.stox.module.charting.drawing;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.typeadapters.PostConstructAdapterFactory;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import com.stox.module.charting.drawing.segment.trend.TrendSegment;
import com.stox.persistence.store.JsonFileStore;
import com.stox.util.JsonConverter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class DrawingRepository {

	@NonNull
	private final Path home;

	private final TypeAdapterFactory drawingTypeAdapterFactory = RuntimeTypeAdapterFactory.of(Drawing.class)
			.registerSubtype(TrendSegment.class, TrendSegment.TYPE);
	private final Gson gson = new GsonBuilder()
			.registerTypeAdapterFactory(drawingTypeAdapterFactory)
			.registerTypeAdapterFactory(new PostConstructAdapterFactory())
			.create();
	private final JsonConverter jsonConverter = new JsonConverter(gson);

	private Path path(final String isin) {
		return home.resolve(Paths.get("charting", "drawings", isin + ".json"));
	}

	@SneakyThrows
	public DrawingRepository persist(@NonNull final String isin, @NonNull final Set<Drawing> drawings) {
		if(drawings.isEmpty()) {
			Files.deleteIfExists(path(isin));
		} else {
			new JsonFileStore<>(path(isin), Set.class, jsonConverter).write(drawings);
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	public Set<Drawing> find(@NonNull final String isin) {
		return (Set<Drawing>) Optional.ofNullable(new JsonFileStore<>(path(isin), JsonConverter.type(HashSet.class, Drawing.class), jsonConverter).read())
				.orElse(Collections.emptySet());
	}

}
