package com.stox.common.ui;

import javafx.application.Platform;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Fx {

	public void run(Runnable runnable) {
		if(null != runnable) {
			if(Platform.isFxApplicationThread()) {
				runnable.run();
			} else {
				Platform.runLater(runnable);
			}
		}
	}

}
