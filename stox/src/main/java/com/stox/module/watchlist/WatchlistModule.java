package com.stox.module.watchlist;

import java.nio.file.Path;
import java.util.function.Supplier;

import com.stox.Context;
import com.stox.fx.widget.Icon;
import com.stox.module.core.model.BarSpan;
import com.stox.module.core.model.Scrip;
import com.stox.module.core.model.intf.HasBarSpan;
import com.stox.module.core.model.intf.HasScrip;
import com.stox.module.watchlist.repository.WatchlistEntryRepository;
import com.stox.module.watchlist.repository.WatchlistRepository;
import com.stox.module.watchlist.widget.AddToWatchlistMenu;
import com.stox.util.JsonConverter;
import com.stox.workbench.module.UiModule;

import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import lombok.NonNull;

public class WatchlistModule extends UiModule<WatchlistViewState> {

	private final WatchlistRepository watchlistRepository;
	private final WatchlistEntryRepository watchlistEntryRepository;
	
	public WatchlistModule(Context context) {
		super(context);
		final Path home = context.getConfig().getHome();
		final JsonConverter jsonConverter = context.getJsonConverter();
		this.watchlistRepository = new WatchlistRepository(home, jsonConverter);
		this.watchlistEntryRepository = new WatchlistEntryRepository(home, jsonConverter);
		context.getContextMenuConfigurers().add(this::configure);
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
	
	private void configure(@NonNull final ContextMenu contextMenu, @NonNull final Object target) {
		if(HasScrip.class.isInstance(target) && HasBarSpan.class.isInstance(target)) {
			final Supplier<Scrip> scripSupplier = () -> HasScrip.class.cast(target).getScrip();
			final Supplier<BarSpan> barSpanSupplier = () -> HasBarSpan.class.cast(target).getBarSpan();
			final Node root = getContext().getWorkbench().getRoot();
			final AddToWatchlistMenu menu = AddToWatchlistMenu.builder()
					.root(root)
					.scripSupplier(scripSupplier)
					.barSpanSupplier(barSpanSupplier)
					.watchlistRepository(watchlistRepository)
					.messageSource(getContext().getMessageSource())
					.watchlistEntryRepository(watchlistEntryRepository)
					.build();
			contextMenu.getItems().add(menu);
		}
	}

}
