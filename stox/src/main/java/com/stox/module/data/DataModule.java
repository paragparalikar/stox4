package com.stox.module.data;

import com.stox.Context;
import com.stox.fx.widget.Icon;
import com.stox.workbench.module.ModuleView;
import com.stox.workbench.module.UiModule;

import javafx.beans.value.ObservableValue;
import lombok.NonNull;

public class DataModule extends UiModule<DataViewState> {

	public DataModule(@NonNull final Context context) {
		super(context);
	}

	@Override
	protected String getIcon() {
		return Icon.DOWNLOAD;
	}

	@Override
	protected String getCode() {
		return "data";
	}

	@Override
	protected ObservableValue<String> getModuleName() {
		return getContext().getMessageSource().get("Data");
	}

	@Override
	protected ModuleView<DataViewState> buildModuleView() {
		final Context context = getContext();
		return DataView.builder()
				.messageSource(context.getMessageSource())
				.executorService(context.getScheduledExecutorService())
				.barRepository(context.getBarRepository())
				.scripRepository(context.getScripRepository())
				.exchangeRepository(context.getExchangeRepository())
				.build();
	}

}
