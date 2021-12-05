package com.stox.module.screener;

import java.util.Collection;
import java.util.function.Supplier;

import com.stox.fx.widget.FxMessageSource;
import com.stox.module.core.widget.supplier.scrip.ScripsSupplierView;
import com.stox.workbench.module.ModuleView;

import javafx.geometry.Bounds;
import lombok.Getter;
import lombok.NonNull;

public class ScreenerView extends ModuleView<ScreenerViewState> {

	@Getter private final ScreenerViewTitleBar titleBar;
	
	public ScreenerView(
			@NonNull final FxMessageSource messageSource,
			@NonNull final Collection<Supplier<ScripsSupplierView>> scripsSupplierViewSuppliers) {
		title(titleBar = new ScreenerViewTitleBar());
	}

	@Override
	public ScreenerViewState stop(Bounds bounds) {
		return super.stop(new ScreenerViewState(), bounds);
	}

}
