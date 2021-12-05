package com.stox.module.screener;

import com.stox.fx.widget.FxMessageSource;
import com.stox.module.watchlist.repository.WatchlistRepository;
import com.stox.workbench.module.ModuleView;

import javafx.geometry.Bounds;
import lombok.Getter;
import lombok.NonNull;

public class ScreenerView extends ModuleView<ScreenerViewState> {

	@Getter private final ScreenerViewTitleBar titleBar;
	
	public ScreenerView(
			@NonNull final FxMessageSource messageSource,
			@NonNull final WatchlistRepository watchlistRepository) {
		title(titleBar = new ScreenerViewTitleBar());
		
	}

	@Override
	public ScreenerViewState stop(Bounds bounds) {
		// TODO Auto-generated method stub
		return null;
	}

}
