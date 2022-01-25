package com.stox.module.ranker;

import java.util.LinkedList;
import java.util.List;

import com.stox.module.core.widget.supplier.scrip.ScripsSupplierView;
import com.stox.module.ranker.model.Ranker;

import lombok.Data;

@Data
public class RankerConfig {

	private int offset = 0;
	private Ranker<?> ranker;
	private Object rankerConfig;
	private final List<ScripsSupplierView> scripsSupplierViews = new LinkedList<>();

}
