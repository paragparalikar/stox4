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
		return ExplorerView.builder()
				.icon(getIcon())
				.titleValue(getModuleName())
				.gson(getContext().getGson())
				.closeConsumer(super::remove)
				.scripRepository(getContext().getScripRepository())
				.build();
	}

}
