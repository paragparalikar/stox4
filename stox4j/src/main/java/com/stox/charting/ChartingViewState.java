package com.stox.charting;

import java.io.Serializable;

import com.stox.charting.axis.x.XAxisState;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChartingViewState implements Serializable {
	private static final long serialVersionUID = -8538563619151369792L;

	private long to;
	private String isin;
	private XAxisState xAxisState;
}
