package com.stox.module.ranker;

import java.util.Collection;
import java.util.function.Supplier;

import com.stox.fx.widget.FxMessageSource;
import com.stox.module.core.widget.supplier.scrip.ScripsSupplierView;
import com.stox.workbench.module.ModuleView;

import javafx.geometry.Bounds;
import lombok.Getter;
import lombok.NonNull;

public class RankerView extends ModuleView<RankerViewState> {
	
	@Getter
	private final RankerTitleBar titleBar;

	public RankerView(
			@NonNull final FxMessageSource messageSource,
			@NonNull final Collection<Supplier<ScripsSupplierView>> scripsSupplierViewSuppliers) {
		titleBar = new RankerTitleBar(messageSource, scripsSupplierViewSuppliers);
		title(titleBar);
	}
	
	@Override
	public RankerViewState stop(Bounds bounds) {
		return super.stop(new RankerViewState(), bounds);
	}

}
