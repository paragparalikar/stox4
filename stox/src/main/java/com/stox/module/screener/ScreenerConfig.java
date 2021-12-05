package com.stox.module.screener;

import java.util.LinkedList;
import java.util.List;

import com.stox.module.core.widget.supplier.scrip.ScripsSupplierView;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ScreenerConfig {

	private int span = 1;
	private int offset = 0;
	private final List<ScripsSupplierView> scripsSupplierViews = new LinkedList<>();
	
}
