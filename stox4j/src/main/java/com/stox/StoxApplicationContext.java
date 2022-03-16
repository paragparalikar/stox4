package com.stox;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.greenrobot.eventbus.EventBus;

import com.stox.common.bar.BarRepository;
import com.stox.common.bar.BarService;
import com.stox.common.scrip.ScripRepository;
import com.stox.common.scrip.ScripService;
import com.stox.data.downloader.bar.EodBarDownloader;
import com.stox.data.downloader.bar.NseEodBarDownloader;
import com.stox.data.downloader.scrip.NseScripMasterDownloader;
import com.stox.data.downloader.scrip.ScripMasterDownloader;
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
	private final EodBarDownloader eodBarDownloader;
	private final ScripMasterDownloader scripMasterDownloader;
	private final ExecutorService executor;

	public StoxApplicationContext() {
		eventBus = new EventBus();
		executor = Executors.newWorkStealingPool();
		home = Paths.get(System.getProperty("user.home"), ".stox4j");
		barRepository = new BarRepository(home);
		barService = new BarService(barRepository);
		scripRepository = new ScripRepository(home);
		scripService = new ScripService(eventBus, scripRepository);
		watchlistRepository = new WatchlistRepository(home);
		watchlistService = new WatchlistService(eventBus, watchlistRepository);
		exampleRepository = new ExampleRepository(home);
		exampleService = new ExampleService(eventBus, exampleRepository);
		exampleGroupRepository = new ExampleGroupRepository(home);
		exampleGroupService = new ExampleGroupService(eventBus, exampleGroupRepository);
		eodBarDownloader = new NseEodBarDownloader(scripService);
		scripMasterDownloader = new NseScripMasterDownloader();
	}
	
}
