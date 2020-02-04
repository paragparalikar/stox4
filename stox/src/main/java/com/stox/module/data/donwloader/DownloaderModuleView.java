package com.stox.module.data.donwloader;

import com.stox.Context;
import com.stox.fx.workbench.ModuleView;
import com.stox.fx.workbench.TitleBar;

import lombok.NonNull;

public class DownloaderModuleView extends ModuleView<DownloaderModuleView> {

	public DownloaderModuleView(@NonNull final Context context) {
		super(context);
	}

	@Override
	protected TitleBar buildTitleBar() {
		return new TitleBar(getContext().getMessageSource().get("Data"));
	}

	@Override
	public DownloaderModuleView getThis() {
		return this;
	}

}
