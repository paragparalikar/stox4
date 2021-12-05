package com.stox.module.screener;

import com.stox.Context;
import com.stox.fx.widget.Icon;
import com.stox.workbench.module.ModuleView;
import com.stox.workbench.module.UiModule;

import javafx.beans.value.ObservableValue;

public class ScreenerModule extends UiModule<ScreenerViewState> {

	public ScreenerModule(Context context) {
		super(context);
	}

	@Override
	protected String getIcon() {
		return Icon.FILTER;
	}

	@Override
	protected String getCode() {
		return "screener";
	}

	@Override
	protected ObservableValue<String> getModuleName() {
		return getContext().getMessageSource().get("Screener");
	}

	@Override
	protected ModuleView<ScreenerViewState> buildModuleView() {
		return new ScreenerView(getContext().getMessageSource(), getContext().getScripsSupplierViewSuppliers());
	}

}
