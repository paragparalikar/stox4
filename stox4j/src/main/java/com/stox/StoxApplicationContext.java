package com.stox;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.greenrobot.eventbus.EventBus;

import com.stox.charting.drawing.DrawingRepository;
import com.stox.charting.drawing.DrawingService;
import com.stox.common.SerializationRepository;
import com.stox.common.SerializationService;
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
	
	private final Path home = Paths.get(System.getProperty("user.home"), ".stox4j");
	private final EventBus eventBus = new EventBus();
	private final BarRepository barRepository = new BarRepository(home);
	private final BarService barService = new BarService(barRepository);
	private final ScripRepository scripRepository = new ScripRepository(home);
	private final ScripService scripService = new ScripService(eventBus, scripRepository);
	private final WatchlistRepository watchlistRepository = new WatchlistRepository(home);
	private final WatchlistService watchlistService = new WatchlistService(eventBus, watchlistRepository);
	private final ExampleRepository exampleRepository = new ExampleRepository(home);
	private final ExampleService exampleService = new ExampleService(eventBus, exampleRepository);
	private final ExampleGroupRepository exampleGroupRepository = new ExampleGroupRepository(home);
	private final ExampleGroupService exampleGroupService = new ExampleGroupService(eventBus, exampleGroupRepository);
	private final EodBarDownloader eodBarDownloader = new NseEodBarDownloader(scripService);
	private final ScripMasterDownloader scripMasterDownloader = new NseScripMasterDownloader();
	private final ExecutorService executor = Executors.newWorkStealingPool();
	private final DrawingRepository drawingRepository = new DrawingRepository(home);
	private final DrawingService drawingService = new DrawingService(drawingRepository);
	private final SerializationRepository serializationRepository = new SerializationRepository(home);
	private final SerializationService serializationService = new SerializationService(serializationRepository);

}
