package com.stox.module.charting.widget;

import com.stox.fx.fluent.scene.layout.FluentGroup;
import com.stox.fx.widget.HasNode;
import com.stox.fx.widget.Ui;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;
import com.stox.module.charting.event.UpdatableRequestEvent;
import com.stox.util.Strings;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Crosshair implements HasNode<Node>{

	private final Region region;
	private final Label valueLabel = new Label();
	private final Line vertical = new Line();
	private final Line horizontal = new Line();
	private final FluentGroup group = new FluentGroup() {
		@Override
		protected void layoutChildren() {

		};
	};

	public Crosshair(final Region region) {
		this.region = region;
		
		group.managed(false).setMouseTransparent(true);
		group.getStyleClass().add("crosshair");
		group.getChildren().addAll(vertical, horizontal, valueLabel);
		
		vertical.getStyleClass().add("vertical");
		horizontal.getStyleClass().add("horizontal");
		bind();
	}

	private void update(final MouseEvent event) {
		vertical.setStartX(Ui.px(event.getX()));
		horizontal.setStartY(Ui.px(event.getY()));
		group.fireEvent(new UpdatableRequestEvent((xAxis, yAxis) -> update(xAxis, yAxis)));
	}

	public void update(final XAxis xAxis,final YAxis yAxis) {
		final double height = 16;
		final double width = xAxis.getWidth();
		valueLabel.setText(Strings.stringValueOf(yAxis.getValue(horizontal.getStartX())));
		valueLabel.resizeRelocate(region.getWidth() - width, horizontal.getStartY() - height / 2, width, height);
	}

	private void bind() {
		bindNodes();
		bindHandlers();
	}

	private void bindNodes() {
		vertical.endXProperty().bind(vertical.startXProperty());
		horizontal.endYProperty().bind(horizontal.startYProperty());
		vertical.startYProperty().bind(region.layoutYProperty());
		vertical.endYProperty().bind(region.heightProperty());
		horizontal.startXProperty().bind(region.layoutXProperty());
		horizontal.endXProperty().bind(region.widthProperty());

		valueLabel.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
		valueLabel.setTextFill(Color.WHITE);
	}

	private void bindHandlers() {
		region.addEventHandler(MouseEvent.MOUSE_MOVED, event -> update(event));
		region.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> update(event));
	}

	@Override
	public Node getNode() {
		return group;
	}
}
