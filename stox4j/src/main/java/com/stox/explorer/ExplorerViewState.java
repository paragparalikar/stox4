package com.stox.explorer;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExplorerViewState implements Serializable {
	private static final long serialVersionUID = -6058244778209184731L;

	private String selectedItem;
	private String firstVisibleItem;
	
}
