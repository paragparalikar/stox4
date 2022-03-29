package com.stox;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoxApplicationState implements Serializable {
	private static final long serialVersionUID = -7921508120472083219L;

	private int selectedTabIndex;
	
}
