package com.stox.charting.axis.x;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class XAxisState implements Serializable {
	private static final long serialVersionUID = 6739822511734205243L;

	private double unitWidth = 5, panWidth = 0;

}
