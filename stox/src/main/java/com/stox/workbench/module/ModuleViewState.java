package com.stox.workbench.module;

import java.io.Serializable;

import lombok.Data;

@Data
public class ModuleViewState implements Serializable{
	private static final long serialVersionUID = -8278951864894232711L;

	private double x, y, width, height;

}
