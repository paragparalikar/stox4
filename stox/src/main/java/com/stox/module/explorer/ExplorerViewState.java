package com.stox.module.explorer;

import com.stox.module.core.model.Exchange;
import com.stox.workbench.module.ModuleViewState;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class ExplorerViewState extends ModuleViewState {
	private static final long serialVersionUID = 1651026735650177597L;

	private final String isin;
	
	private final Exchange exchange;
	
	private final boolean searchVisible;
	
	private final boolean filterVisible;
	
}
