package com.stox.example;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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
	
	public synchronized List<ExampleGroup> findAll(){
		if(cache.isEmpty()) {
			final List<ExampleGroup> groups = repository.findAll();
			groups.forEach(group -> cache.put(group.getId(), group));
		}
		return new ArrayList<>(cache.values());
	}
	
	public ExampleGroup create(ExampleGroup exampleGroup) {
		if(existsByName(exampleGroup.getName())) throw new IllegalArgumentException(String.format("Example Group with name %s already exists", exampleGroup.getName()));
		if(null != exampleGroup.getId()) throw new IllegalArgumentException("Id must be null for creating a resource");
		exampleGroup.setId(UUID.randomUUID().toString());
		cache.put(exampleGroup.getId(), exampleGroup);
		repository.saveAll(cache.values());
		eventBus.post(new ExampleGroupCreatedEvent(exampleGroup));
		return exampleGroup;
	}
	
	public ExampleGroup update(ExampleGroup exampleGroup) {
		if(existsByName(exampleGroup.getName())) throw new IllegalArgumentException(String.format("Example Group with name %s already exists", exampleGroup.getName()));
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

	public boolean existsByName(String name){
		if(null == name || name.isEmpty()) return false;
		return findAll().stream()
				.map(ExampleGroup::getName)
				.anyMatch(exampleGroupName -> exampleGroupName.trim().equalsIgnoreCase(name.trim()));
	}

}
