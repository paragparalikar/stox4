package com.stox.module.explorer;

import com.stox.Context;
import com.stox.workbench.module.ModuleView;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableView;
import lombok.NonNull;

public class ExplorerView extends ModuleView<ExplorerView> {

	public ExplorerView(@NonNull final String icon,@NonNull final ObservableValue<String> titleValue,@NonNull final Context context) {
		super(icon, titleValue, context);
		//center(new TableView<Object>());
	}

	@Override
	public ExplorerView getThis() {
		return this;
	}

}
