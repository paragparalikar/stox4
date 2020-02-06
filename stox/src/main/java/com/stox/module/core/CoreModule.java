package com.stox.module.core;

import com.stox.Context;
import com.stox.workbench.module.Module;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CoreModule implements Module {

	@NonNull
	private final Context context;

	@Override
	public void start() {
		context.getScheduledExecutorService().submit(context.getScripRepository()::init);
	}

	@Override
	public void stop() {
		context.getBarRepository().close();
	}

}
