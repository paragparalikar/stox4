package com.stox.module.ranker;

import java.util.Collection;
import java.util.function.Supplier;

import com.stox.fx.fluent.scene.control.FluentProgressBar;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.search.SearchableListView;
import com.stox.module.core.model.Scrip;
import com.stox.module.core.persistence.BarRepository;
import com.stox.module.core.widget.supplier.scrip.ScripsSupplierView;
import com.stox.workbench.module.ModuleView;

import javafx.geometry.Bounds;
import lombok.Getter;
import lombok.NonNull;

public class RankerView extends ModuleView<RankerViewState> {
	
	@Getter private final RankerTitleBar titleBar;
	private final FluentProgressBar progressBar = new FluentProgressBar().fullWidth().classes("primary", "success");
	private final SearchableListView<Scrip> listView = new SearchableListView<>();

	public RankerView(
			@NonNull final FxMessageSource messageSource,
			@NonNull final BarRepository barRepository,
			@NonNull final Collection<Supplier<ScripsSupplierView>> scripsSupplierViewSuppliers) {
		titleBar = new RankerTitleBar(messageSource, scripsSupplierViewSuppliers);
		title(titleBar);
		content(listView);
	}
	
	@Override
	public RankerViewState stop(Bounds bounds) {
		return super.stop(new RankerViewState(), bounds);
	}

}
