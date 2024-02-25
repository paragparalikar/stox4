package com.stox;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.greenrobot.eventbus.EventBus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stox.alert.AlertRepository;
import com.stox.alert.AlertService;
import com.stox.charting.drawing.DrawingRepository;
import com.stox.charting.drawing.DrawingService;
import com.stox.common.bar.BarRepository;
import com.stox.common.bar.BarService;
import com.stox.common.persistence.SerializationRepository;
import com.stox.common.persistence.SerializationService;
import com.stox.common.quote.NseQuoteProvider;
import com.stox.common.quote.QuoteService;
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
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final Path home = Paths.get(System.getProperty("user.home"), ".stox4j");
	private final EventBus eventBus = new EventBus();
	private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
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
	private final AlertRepository alertRepository = new AlertRepository(home);
	private final QuoteService quoteService = new QuoteService(new NseQuoteProvider(objectMapper));
	private final AlertService alertService = new AlertService(scripService, quoteService, alertRepository);
	private final EodBarDownloader eodBarDownloader = new NseEodBarDownloader(scripService);
	private final ScripMasterDownloader scripMasterDownloader = new NseScripMasterDownloader();
	private final ExecutorService executor = Executors.newWorkStealingPool();
	private final DrawingRepository drawingRepository = new DrawingRepository(home);
	private final DrawingService drawingService = new DrawingService(drawingRepository);
	private final SerializationRepository serializationRepository = new SerializationRepository(home);
	private final SerializationService serializationService = new SerializationService(serializationRepository);

}
