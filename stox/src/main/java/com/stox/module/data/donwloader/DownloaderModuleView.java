package com.stox.module.data.donwloader;

import com.stox.Context;
import com.stox.fx.workbench.ModuleView;

import javafx.beans.value.ObservableValue;
import lombok.NonNull;

public class DownloaderModuleView extends ModuleView<DownloaderModuleView> {

	public DownloaderModuleView(@NonNull final String icon, @NonNull final ObservableValue<String> titleValue, @NonNull final Context context) {
		super(icon, titleValue, context);
	}

	@Override
	public DownloaderModuleView getThis() {
		return this;
	}

}
