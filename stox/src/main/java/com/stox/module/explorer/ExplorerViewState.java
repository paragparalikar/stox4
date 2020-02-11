package com.stox.module.explorer;

import com.google.gson.annotations.SerializedName;
import com.stox.module.core.model.Exchange;
import com.stox.workbench.module.ModuleViewState;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
public class ExplorerViewState extends ModuleViewState{

	@SerializedName("isin")
	private String isin;
	
	@SerializedName("exchange")
	private Exchange exchange;
	
	@SerializedName("searchVisible")
	private boolean searchVisible;

	@SerializedName("searchText")
	private String searchText;
}
