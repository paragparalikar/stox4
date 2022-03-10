package com.stox.example;

import java.util.Collections;
import java.util.Set;

import org.greenrobot.eventbus.EventBus;

import com.stox.example.event.ExampleAddedEvent;
import com.stox.example.event.ExampleGroupClearedEvent;
import com.stox.example.event.ExampleRemovedEvent;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExampleService {

	private final EventBus eventBus;
	private final ExampleRepository repository;
	
	public Set<Example> findByGroupId(String groupId){
		return repository.findByGroupId(groupId);
	}
	
	public void create(Example example) {
		final String groupId = example.getGroupId();
		final Set<Example> examples = findByGroupId(groupId);
		if(examples.add(example)) {
			repository.saveAll(groupId, examples);
			eventBus.post(new ExampleAddedEvent(example));
		}
	}
	
	public void delete(Example example) {
		final String groupId = example.getGroupId();
		final Set<Example> examples = findByGroupId(groupId);
		if(examples.remove(example)) {
			repository.saveAll(groupId, examples);
			eventBus.post(new ExampleRemovedEvent(example));
		}
	}
	
	public void clear(String groupId) {
		repository.saveAll(groupId, Collections.emptyList());
		eventBus.post(new ExampleGroupClearedEvent(groupId));
	}
}
