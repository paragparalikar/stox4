package com.stox.persistence;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.stox.module.core.model.intf.HasId;
import com.stox.util.JsonConverter;

public class CachingRepository<T extends HasId<Integer>> extends SimpleRepository<T> {

	private final Map<Integer,T> cache = new HashMap<>();
	private Supplier<Collection<T>> supplier = this::load;
	
	public CachingRepository(Path path, Class<T> type, JsonConverter jsonConverter) {
		super(path, type, jsonConverter);
	}
	
	@Override
	public T find(Integer id) {
		return cache.computeIfAbsent(id, super::find);
	}

	@Override
	public List<T> findAll() {
		return new ArrayList<>(supplier.get());
	}
	
	private List<T> load(){
		final List<T> entities = super.findAll();
		cache.putAll(entities.stream().collect(Collectors.toMap(T::getId, Function.identity())));
		supplier = cache::values;
		return entities;
	}

	@Override
	public Integer save(T entity) {
		final Integer id = super.save(entity);
		cache.put(id, entity);
		return id;
	}

	@Override
	public void update(T entity) {
		super.update(entity);
		cache.put(entity.getId(), entity);
	}

	@Override
	public void delete(Integer id) {
		super.delete(id);
		cache.remove(id);
	}

}
