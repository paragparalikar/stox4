package com.stox.module.explorer;

import com.google.gson.annotations.SerializedName;
import com.stox.module.core.model.Exchange;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class ExplorerViewState {

	@SerializedName("isin")
	private String isin;
	
	@SerializedName("exchange")
	private Exchange exchange;
	
	@SerializedName("searchVisible")
	private boolean searchVisible;

	@SerializedName("searchText")
	private String searchText;
}
