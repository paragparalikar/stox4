package com.stox.module.ranker;

import com.stox.Context;
import com.stox.fx.widget.Icon;
import com.stox.workbench.module.ModuleView;
import com.stox.workbench.module.UiModule;

import javafx.beans.value.ObservableValue;

public class RankerModule extends UiModule<RankerViewState> {

	public RankerModule(Context context) {
		super(context);
	}

	@Override
	protected String getIcon() {
		return Icon.ALIGN_RIGHT;
	}

	@Override
	protected String getCode() {
		return "ranker";
	}

	@Override
	protected ObservableValue<String> getModuleName() {
		return getContext().getMessageSource().get("Ranker");
	}

	@Override
	protected ModuleView<RankerViewState> buildModuleView() {
		return new RankerView(
				getContext().getMessageSource(), 
				getContext().getBarRepository(),
				getContext().getScripsSupplierViewSuppliers());
	}

}
