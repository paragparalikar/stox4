package com.stox.module.explorer;

import com.stox.Context;
import com.stox.fx.widget.Icon;
import com.stox.workbench.module.UiModule;

import javafx.beans.value.ObservableValue;
import lombok.NonNull;

public class ExplorerModule extends UiModule {

	public ExplorerModule(@NonNull final Context context) {
		super(context);
	}
	
	@Override
	protected String getIcon() {
		return Icon.LIST;
	}
	
	@Override
	protected String getCode() {
		return "explorer";
	}

	@Override
	protected ObservableValue<String> getModuleName() {
		return getContext().getMessageSource().get("Instrument Explorer");
	}

	@Override
	protected ExplorerView buildModuleView() {
		return new ExplorerView(getContext().getGson(), getContext().getScripRepository());
	}

}
