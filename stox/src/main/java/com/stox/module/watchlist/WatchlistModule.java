package com.stox.module.watchlist;

import java.nio.file.Path;

import com.stox.Context;
import com.stox.fx.widget.Icon;
import com.stox.module.watchlist.repository.WatchlistEntryRepository;
import com.stox.module.watchlist.repository.WatchlistRepository;
import com.stox.util.JsonConverter;
import com.stox.workbench.module.UiModule;

import javafx.beans.value.ObservableValue;

public class WatchlistModule extends UiModule<WatchlistViewState> {

	private final WatchlistRepository watchlistRepository;
	private final WatchlistEntryRepository watchlistEntryRepository;
	
	public WatchlistModule(Context context) {
		super(context);
		final Path home = context.getConfig().getHome();
		final JsonConverter jsonConverter = context.getJsonConverter();
		this.watchlistRepository = new WatchlistRepository(home, jsonConverter);
		this.watchlistEntryRepository = new WatchlistEntryRepository(home, jsonConverter);
	}

	@Override
	protected String getIcon() {
		return Icon.BOOKMARK;
	}

	@Override
	protected String getCode() {
		return "watchlist";
	}

	@Override
	protected ObservableValue<String> getModuleName() {
		return getContext().getMessageSource().get("Watchlists");
	}

	@Override
	protected WatchlistView buildModuleView() {
		return new WatchlistView(getContext().getMessageSource(), watchlistRepository, watchlistEntryRepository);
	}

}
