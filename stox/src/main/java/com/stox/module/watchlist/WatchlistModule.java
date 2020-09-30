package com.stox.module.watchlist;

import java.nio.file.Path;
import java.util.function.Supplier;

import com.stox.Context;
import com.stox.fx.widget.Icon;
import com.stox.module.core.model.BarSpan;
import com.stox.module.core.model.Scrip;
import com.stox.module.core.model.intf.HasBarSpan;
import com.stox.module.core.model.intf.HasScrip;
import com.stox.module.watchlist.repository.FileWatchlistRepository;
import com.stox.module.watchlist.repository.WatchlistRepository;
import com.stox.module.watchlist.widget.AddToWatchlistMenu;
import com.stox.workbench.module.UiModule;

import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import lombok.NonNull;

public class WatchlistModule extends UiModule<WatchlistViewState> {

	private final WatchlistRepository watchlistRepository;
	
	public WatchlistModule(Context context) {
		super(context);
		final Path home = context.getConfig().getHome();
		this.watchlistRepository = new FileWatchlistRepository(home, context.getScripRepository());
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
		return new WatchlistView(getContext().getMessageSource(), watchlistRepository);
	}
	
	private void configure(@NonNull final ContextMenu contextMenu, @NonNull final Object target) {
		if(HasScrip.class.isInstance(target) && HasBarSpan.class.isInstance(target)) {
			final Supplier<Scrip> scripSupplier = () -> HasScrip.class.cast(target).scrip();
			final Supplier<BarSpan> barSpanSupplier = () -> HasBarSpan.class.cast(target).barSpan();
			final Node root = getContext().getWorkbench().getRoot();
			final AddToWatchlistMenu menu = AddToWatchlistMenu.builder()
					.root(root)
					.scripSupplier(scripSupplier)
					.barSpanSupplier(barSpanSupplier)
					.watchlistRepository(watchlistRepository)
					.messageSource(getContext().getMessageSource())
					.build();
			contextMenu.getItems().add(menu);
		}
	}

}
