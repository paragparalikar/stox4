package com.stox.workbench.link;

import com.stox.module.core.model.BarSpan;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class PersistentLinkState {

	private long to;
	
	private String isin	;
	
	private BarSpan barSpan; 
	
	public PersistentLinkState(@NonNull final Link link) {
		
	}

}
