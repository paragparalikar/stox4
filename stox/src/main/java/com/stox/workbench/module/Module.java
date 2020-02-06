package com.stox.workbench.module;

import com.stox.Context;

public interface Module {

	void start(Context context);
	
	void stop();
	
}
