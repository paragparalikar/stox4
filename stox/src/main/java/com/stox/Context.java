package com.stox;

import java.util.concurrent.ScheduledExecutorService;

import com.stox.fx.widget.FxMessageSource;
import com.stox.util.JsonConverter;
import com.stox.workbench.Workbench;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Context {
	
	@NonNull
	private final Config config;
	
	@NonNull
	private final Workbench workbench;
	
	@NonNull
	private final JsonConverter jsonConverter;

	@NonNull
	private final FxMessageSource messageSource;
	
	@NonNull
	private final ScheduledExecutorService scheduledExecutorService;
}
