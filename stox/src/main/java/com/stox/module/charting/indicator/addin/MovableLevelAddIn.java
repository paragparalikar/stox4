package com.stox.module.charting.indicator.addin;

import java.util.concurrent.Callable;

import com.stox.fx.widget.Ui;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;
import lombok.AccessLevel;
import lombok.Getter;

public class MovableLevelAddIn<M> extends LevelAddIn<M> {

	@Getter(AccessLevel.PROTECTED)
	private boolean movable;
	private double updatedY = Double.MIN_VALUE;
	private Region parentRegion;
	private EventHandler<MouseEvent> dragHandler = event -> mouseDragged(event);
	private EventHandler<MouseEvent> releaseHandler = event -> mouseReleased(event);

	public MovableLevelAddIn(Callable<Double> updater) {
		super(updater);
		final Line line = getLine();
		line.setCursor(Cursor.MOVE);
		line.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> mousePressed(event));
	}

	private void bindParentListeners() {
		if (null == parentRegion) {
			parentRegion = Ui.getParentOfType(Region.class, getNode());
			if (null != parentRegion) {
				parentRegion.addEventHandler(MouseEvent.MOUSE_DRAGGED, dragHandler);
				parentRegion.addEventHandler(MouseEvent.MOUSE_RELEASED, releaseHandler);
			}
		}
	}

	@Override
	public void preLayoutChartChildren(XAxis xAxis, YAxis yAxis) {
		if (updatedY > Double.MIN_VALUE) {
			setLevel(yAxis.getValue(updatedY));
			updatedY = Double.MIN_VALUE;
		}
		bindParentListeners();
		super.preLayoutChartChildren(xAxis, yAxis);
	}

	protected void mousePressed(final MouseEvent event) {
		movable = true;
		bindParentListeners();
	}

	protected void mouseDragged(final MouseEvent event) {
		if (movable) {
			getLine().setStartY(Ui.px(event.getY()));
		}
	}

	protected void mouseReleased(final MouseEvent event) {
		if (movable) {
			movable = false;
			updatedY = event.getY();
		}
	}

}
