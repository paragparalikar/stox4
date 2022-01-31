package com.stox.module.ranker;

import com.stox.module.ranker.model.Ranker;

import lombok.Data;

@Data
public class RankerConfig {

	private int offset = 0;
	private Ranker<?> ranker;
	private Object rankerConfig;

}
