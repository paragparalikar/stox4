package com.stox;

import java.util.concurrent.ScheduledExecutorService;

import com.stox.fx.widget.FxMessageSource;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class Context {

	@NonNull
	private final FxMessageSource messageSource;
	
	@NonNull
	private final ScheduledExecutorService scheduledExecutorService;
}
