package com.stox.watchlist.menu;

import java.util.List;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTableMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.stox.common.ui.Fx;
import com.stox.common.ui.Icon;
import com.stox.watchlist.Watchlist;
import com.stox.watchlist.WatchlistService;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

public class DownloadMenuItem extends MenuItem implements EventHandler<ActionEvent> {

	private final WatchlistService watchlistService;
	
	public DownloadMenuItem(WatchlistService watchlistService) {
		super("Download All", Fx.icon(Icon.DOWNLOAD));
		this.watchlistService = watchlistService;
		setOnAction(this);
	}

	@Override
	public void handle(ActionEvent event) {
		final DynamoDBTableMapper<Watchlist, String, Object> mapper = 
				new DynamoDBMapper(AmazonDynamoDBClientBuilder.standard()
				.withRegion(Regions.AP_SOUTH_1)
				.build()).newTableMapper(Watchlist.class);
		mapper.createTableIfNotExists(new ProvisionedThroughput(5l,5l));
		final PaginatedScanList<Watchlist> list = mapper.scan(new DynamoDBScanExpression());
		final List<Watchlist> backupWatchlists = watchlistService.findAll();
		
	}
	
}
