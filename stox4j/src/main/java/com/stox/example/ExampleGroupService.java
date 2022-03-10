package com.stox.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.greenrobot.eventbus.EventBus;

import com.stox.example.event.ExampleGroupCreatedEvent;
import com.stox.example.event.ExampleGroupDeletedEvent;
import com.stox.example.event.ExampleGroupUpdatedEvent;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExampleGroupService {

	private final EventBus eventBus;
	private final ExampleGroupRepository repository;
	private final Map<String, ExampleGroup> cache = new HashMap<>();
	
	public List<ExampleGroup> findAll(){
		if(cache.isEmpty()) {
			final List<ExampleGroup> groups = repository.findAll();
			groups.forEach(group -> cache.put(group.getId(), group));
			return groups;
		}
		return new ArrayList<>(cache.values());
	}
	
	public ExampleGroup create(ExampleGroup exampleGroup) {
		if(null != exampleGroup.getId()) throw new IllegalArgumentException("Id must be null for creating a resource");
		exampleGroup.setId(UUID.randomUUID().toString());
		cache.put(exampleGroup.getId(), exampleGroup);
		repository.saveAll(cache.values());
		eventBus.post(new ExampleGroupCreatedEvent(exampleGroup));
		return exampleGroup;
	}
	
	public ExampleGroup udpate(ExampleGroup exampleGroup) {
		if(null == exampleGroup.getId()) throw new IllegalArgumentException("Id must not be null for updating a resource");
		cache.put(exampleGroup.getId(), exampleGroup);
		repository.saveAll(cache.values());
		eventBus.post(new ExampleGroupUpdatedEvent(exampleGroup));
		return exampleGroup;
	}
	
	public ExampleGroup deleteById(String id) {
		final ExampleGroup exampleGroup = cache.remove(id);
		repository.saveAll(cache.values());
		eventBus.post(new ExampleGroupDeletedEvent(exampleGroup));
		return exampleGroup;
	}
}
