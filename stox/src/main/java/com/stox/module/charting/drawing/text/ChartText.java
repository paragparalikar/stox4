package com.stox.module.charting.drawing.text;

import java.util.Optional;

import com.stox.fx.fluent.scene.layout.FluentGroup;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;
import com.stox.module.charting.drawing.AbstractDrawing;
import com.stox.module.charting.drawing.Drawing;
import com.stox.module.charting.drawing.Location;
import com.stox.module.charting.event.UpdatableRequestEvent;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class ChartText extends AbstractDrawing<ChartTextState> implements EventHandler<MouseEvent> {

	private volatile TextField textField;
	private final Text text = new Text();
	private final Location location = new Location();
	private final Group group = new FluentGroup().classes("drawing");
	private final EventHandler<KeyEvent> textFieldEventHandler = event -> {
		if (KeyCode.ENTER.equals(event.getCode())) {
			commit();
			event.consume();
		} else if (KeyCode.ESCAPE.equals(event.getCode())) {
			rollback();
			event.consume();
		}
	};

	public ChartText() {
		text.setManaged(false);
		group.setManaged(false);
		text.setCursor(Cursor.MOVE);
		text.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
//		final NodeRelocationDecorator nodeRelocationDecorator = new NodeRelocationDecorator(group);
//		nodeRelocationDecorator.setMoveCallback(() -> group.fireEvent(new UpdatableRequestEvent(this)));
//		nodeRelocationDecorator.attach();
		bind();
	}

	public void edit() {
		if (null == textField) {
			textField = new TextField();
			textField.setText(text.getText());
			textField.addEventHandler(KeyEvent.KEY_PRESSED, textFieldEventHandler);
			textField.requestFocus();
			group.getChildren().setAll(textField);
		}
	}

	public void commit() {
		if (null != textField) {
			text.setText(textField.getText());
			group.getChildren().setAll(text);
			textField = null;
		}
	}

	public void rollback() {
		group.getChildren().setAll(text);
		textField = null;
	}

	@Override
	public Node getNode() {
		return group;
	}

	@Override
	public void update(XAxis xAxis, YAxis yAxis) {
		location.date(xAxis.getDate(group.getLayoutX()));
		location.value(yAxis.getValue(group.getLayoutY()));
	}

	public void move(final double screenX, final double screenY) {
		final Parent parent = group.getParent();
		if (null != parent) {
			final Point2D point = parent.screenToLocal(screenX, screenY);
			group.setLayoutX(point.getX());
			group.setLayoutY(point.getY());
			group.fireEvent(new UpdatableRequestEvent(this));
		}
	}

	@Override
	public void handle(MouseEvent event) {
		if (MouseButton.PRIMARY.equals(event.getButton()) && !event.isConsumed()) {
			if (MouseEvent.MOUSE_CLICKED.equals(event.getEventType()) && 2 == event.getClickCount()) {
				edit();
			}
		}
	}

	@Override
	public void layoutChartChildren(XAxis xAxis, YAxis yAxis) {
		group.setLayoutX(xAxis.getX(location.date()));
		group.setLayoutY(yAxis.getY(location.value()));
	}

	@Override
	public ChartTextState state() {
		return new ChartTextState().location(location).text(text.getText());
	}

	@Override
	public Drawing<ChartTextState> state(ChartTextState state) {
		Optional.ofNullable(state).ifPresent(value -> {
			location.state(state.location());
			text.setText(state.text());  // on fx thread ? 
		});
		return this;
	}

}
