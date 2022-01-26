package com.stox.common.scrip;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Service
@RequiredArgsConstructor
public class ScripService {

	private final ScripRepository scripRepository;
	private final Map<String, Scrip> isinScripMapping = new HashMap<>();
	private final Map<String, Scrip> codeScripMapping = new HashMap<>();
	private final Map<String, Scrip> nameScripMapping = new HashMap<>();
	
	@SneakyThrows
	private void loadIfRequired() {
		if(isinScripMapping.isEmpty()) cache(scripRepository.findAll());
	}
	
	private void cache(Collection<Scrip> scrips) {
		scrips.forEach(scrip -> {
			isinScripMapping.put(scrip.getIsin(), scrip);
			codeScripMapping.put(scrip.getCode(), scrip);
			nameScripMapping.put(scrip.getName(), scrip);
		});
	}
	
	public Collection<Scrip> findAll(){
		loadIfRequired();
		return isinScripMapping.values();
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
	}
}
