package com.stox;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.greenrobot.eventbus.EventBus;

import com.stox.common.bar.BarRepository;
import com.stox.common.bar.BarService;
import com.stox.common.scrip.ScripRepository;
import com.stox.common.scrip.ScripService;
import com.stox.example.ExampleGroupRepository;
import com.stox.example.ExampleGroupService;
import com.stox.example.ExampleRepository;
import com.stox.example.ExampleService;
import com.stox.watchlist.WatchlistRepository;
import com.stox.watchlist.WatchlistService;

import lombok.Value;

@Value
public class StoxApplicationContext {
	
	private final Path home;
	private final EventBus eventBus;
	private final BarService barService;
	private final BarRepository barRepository;
	private final ScripService scripService;
	private final ScripRepository scripRepository;
	private final WatchlistService watchlistService;
	private final WatchlistRepository watchlistRepository;
	private final ExampleService exampleService;
	private final ExampleRepository exampleRepository;
	private final ExampleGroupService exampleGroupService;
	private final ExampleGroupRepository exampleGroupRepository;

	public StoxApplicationContext() {
		eventBus = new EventBus();
		home = Paths.get(System.getProperty("user.home"), ".stox4j");
		barRepository = new BarRepository();
		barService = new BarService(barRepository);
		scripRepository = new ScripRepository();
		scripService = new ScripService(scripRepository);
		watchlistRepository = new WatchlistRepository(home);
		watchlistService = new WatchlistService(eventBus, watchlistRepository);
		exampleRepository = new ExampleRepository(home);
		exampleService = new ExampleService(eventBus, exampleRepository);
		exampleGroupRepository = new ExampleGroupRepository(home);
		exampleGroupService = new ExampleGroupService(eventBus, exampleGroupRepository);
	}
	
}
