package com.stox.module.data.donwloader;

import com.stox.Context;
import com.stox.fx.widget.Icon;
import com.stox.fx.workbench.Module;
import com.stox.fx.workbench.ModuleView;

import javafx.beans.value.ObservableValue;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DownloaderModule implements Module{

	@NonNull
	private final Context context;
	
	@Override
	public String getId() {
		return "downloader";
	}
	
	@Override
	public String getIcon() {
		return Icon.DOWNLOAD;
	}

	@Override
	public ObservableValue<String> getName() {
		return context.getMessageSource().get("Downloader");
	}

	@Override
	public ModuleView<?> buildModuleView() {
		return new DownloaderModuleView(getIcon(), getName(), context);
	}

}
