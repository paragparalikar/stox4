package com.stox;

import java.util.concurrent.ScheduledExecutorService;

import com.stox.fx.widget.FxMessageSource;
import com.stox.module.core.persistence.BarRepository;
import com.stox.module.core.persistence.ExchangeRepository;
import com.stox.module.core.persistence.ScripRepository;
import com.stox.util.JsonConverter;
import com.stox.workbench.Workbench;
import com.stox.workbench.module.ModuleStateRepository;

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
	

	@NonNull
	private final ExchangeRepository exchangeRepository;
	
	@NonNull
	private final ScripRepository scripRepository;

	@NonNull
	private final BarRepository barRepository;
	
	@NonNull
	private final ModuleStateRepository moduleStateRepository;
}
