package com.stox;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.BiConsumer;

import com.stox.fx.widget.FxMessageSource;
import com.stox.module.core.persistence.BarRepository;
import com.stox.module.core.persistence.ExchangeRepository;
import com.stox.module.core.persistence.ScripRepository;
import com.stox.util.EventBus;
import com.stox.util.JsonConverter;
import com.stox.workbench.Workbench;
import com.stox.workbench.module.ModuleStateRepository;

import javafx.scene.control.ContextMenu;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Context {
	
	@NonNull
	private final Config config;
	
	@NonNull
	private final EventBus eventBus;
		
	@NonNull
	private final Workbench workbench;
	
	@NonNull
	private final JsonConverter jsonConverter;
	
	@NonNull
	private final FxMessageSource messageSource;
	
	@NonNull
	private final ScheduledExecutorService scheduledExecutorService;
	
	private final Set<BiConsumer<ContextMenu,Object>> contextMenuConfigurers = new HashSet<>();
	

	@NonNull
	private final ExchangeRepository exchangeRepository;
	
	@NonNull
	private final ScripRepository scripRepository;

	@NonNull
	private final BarRepository barRepository;
	
	@NonNull
	private final ModuleStateRepository moduleStateRepository;
}
