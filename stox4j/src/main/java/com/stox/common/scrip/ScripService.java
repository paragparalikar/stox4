package com.stox.common.scrip;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.greenrobot.eventbus.EventBus;

import com.stox.common.event.ScripMasterDownloadedEvent;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class ScripService {

	private final EventBus eventBus;
	private final ScripRepository scripRepository;
	private final List<Scrip> scrips = new LinkedList<>();
	private final Map<String, Scrip> isinScripMapping = new HashMap<>();
	private final Map<String, Scrip> codeScripMapping = new HashMap<>();
	private final Map<String, Scrip> nameScripMapping = new HashMap<>();
	
	@SneakyThrows
	private void loadIfRequired() {
		if(isinScripMapping.isEmpty()) cache(scripRepository.findAll());
	}
	
	private void cache(Collection<Scrip> scrips) {
		this.scrips.clear();
		this.scrips.addAll(scrips);
		scrips.forEach(scrip -> {
			isinScripMapping.put(scrip.getIsin(), scrip);
			codeScripMapping.put(scrip.getCode(), scrip);
			nameScripMapping.put(scrip.getName(), scrip);
		});
	}
	
	public List<Scrip> findAll(){
		loadIfRequired();
		return scrips;
	}
	
	public Scrip findByIsin(String isin) {
		loadIfRequired();
		return isinScripMapping.get(isin);
	}
	
	public Scrip findByCode(String code) {
		loadIfRequired();
		return codeScripMapping.get(code);
	}
	
	public Scrip findByName(String name) {
		loadIfRequired();
		return nameScripMapping.get(name);
	}
	
	public void saveAll(Collection<Scrip> scrips) {
		scripRepository.saveAll(scrips);
		cache(scrips);
		eventBus.post(new ScripMasterDownloadedEvent(this.scrips));
	}
	
	public ZonedDateTime getLastModifiedDate() {
		return scripRepository.getLastModifiedDate();
	}
}
