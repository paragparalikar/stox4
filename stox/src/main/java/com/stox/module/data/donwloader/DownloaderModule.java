package com.stox.module.data.donwloader;

import com.stox.Context;
import com.stox.workbench.module.Module;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DownloaderModule implements Module{

	@NonNull
	private final Context context;
	
	@Override
	public void start(Context context) {
		
	}

	@Override
	public void stop() {
		
	}

}
