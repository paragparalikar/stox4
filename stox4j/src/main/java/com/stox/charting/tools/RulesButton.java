package com.stox.charting.tools;

import com.dlsc.workbenchfx.Workbench;
import com.dlsc.workbenchfx.model.WorkbenchDialog;
import com.dlsc.workbenchfx.model.WorkbenchDialog.Type;
import com.stox.charting.ChartingContext;
import com.stox.charting.ChartingView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class RulesButton extends Button implements EventHandler<ActionEvent> {

	private final Workbench workbench;
	private final ChartingContext context;
	//private final ChartingView chartingView;
	
	public RulesButton(Workbench workbench, ChartingContext context) {
		super("Rules");
		setOnAction(this);
		this.context = context;
		this.workbench = workbench;
	}

	@Override
	public void handle(ActionEvent event) {
		final WorkbenchDialog workbenchDialog = WorkbenchDialog
				.builder("", "Hello", Type.INPUT).build();
		workbench.showDialog(workbenchDialog);
	}
	
}
