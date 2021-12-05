package com.stox.module.screener;

import java.util.Collection;
import java.util.function.Supplier;

import com.stox.fx.fluent.scene.control.FluentProgressBar;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.search.SearchableListView;
import com.stox.module.core.model.Scrip;
import com.stox.module.core.widget.supplier.scrip.ScripsSupplierView;
import com.stox.workbench.module.ModuleView;

import javafx.geometry.Bounds;
import lombok.Getter;
import lombok.NonNull;

public class ScreenerView extends ModuleView<ScreenerViewState> {

	@Getter private final ScreenerTitleBar titleBar;
	private final ScreenerService screenerService = new ScreenerService();
	private final FluentProgressBar progressBar = new FluentProgressBar();
	private final SearchableListView<Scrip> listView = new SearchableListView<>();
	
	public ScreenerView(
			@NonNull final FxMessageSource messageSource,
			@NonNull final Collection<Supplier<ScripsSupplierView>> scripsSupplierViewSuppliers) {
		title(titleBar = new ScreenerTitleBar(messageSource, listView, screenerService, scripsSupplierViewSuppliers));
		screenerService.runningProperty().addListener((v, o, n) -> tool(n ? progressBar : null));
		progressBar.progressProperty().bind(screenerService.progressProperty());
		listView.setItems(screenerService.getScrips());
	}

	@Override
	public ScreenerViewState stop(Bounds bounds) {
		return super.stop(new ScreenerViewState(), bounds);
	}

}
