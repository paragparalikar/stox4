package com.stox.module.watchlist;

import com.google.gson.annotations.SerializedName;
import com.stox.module.core.model.BarSpan;
import com.stox.workbench.module.ModuleViewState;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
public class WatchlistViewState extends ModuleViewState {

	@SerializedName("name")
	private String name;
	
	@SerializedName("isin")
	private String isin;
	
	@SerializedName("barSpan")
	private BarSpan barSpan;
	
	@SerializedName("searchVisible")
	private boolean searchVisible;

	@SerializedName("searchText")
	private String searchText;
	
}
