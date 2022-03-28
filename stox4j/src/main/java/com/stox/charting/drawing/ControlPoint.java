package com.stox.charting.drawing;

import com.stox.charting.ChartingContext;
import com.stox.charting.axis.x.XAxis;
import com.stox.charting.axis.y.YAxis;
import com.stox.charting.chart.Chart;
import com.stox.charting.drawing.ControlPoint.ControlPointState;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ControlPoint extends Circle implements Drawing<ControlPointState> {
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ControlPointState implements DrawingState {
		private static final long serialVersionUID = -7122680396650334276L;
		private long date;
		private double price;
		@Override
		public ControlPoint create(Chart chart) {
			return new ControlPoint(chart, this);
		}
	}
	
	private final Chart chart;
	private final XAxis xAxis;
	private final YAxis yAxis;
	private final ChartingContext context;
	private final ControlPointState state;
	
	public ControlPoint(Chart chart, ControlPointState state) {
		this.chart = chart;
		this.state = state;
		this.yAxis = chart.getYAxis();
		this.xAxis = chart.getChartingView().getXAxis();
		this.context = chart.getChartingView().getContext();
		
		setRadius(5);
		setManaged(false);
		getStyleClass().add("control-point");
		centerXProperty().addListener((o,old,val) -> updateDate());
		centerYProperty().addListener((o,old,val) -> updatePrice());
		addEventHandler(MouseEvent.MOUSE_DRAGGED, this::onMouseDragged);
	}
	
	@Override
	public void draw() {
		updateY();
		updateX(); 
	}
	
	private void updateY() {
		setCenterY(yAxis.getY(state.getPrice()));
	}
	
	private void updateX() {
		final int startIndex = 0;
		final int endIndex = context.getBarCount() - 1;
		final double endX = xAxis.getX(endIndex);
		final double startX = xAxis.getX(startIndex);
		final long endDate = context.getBar(endIndex).getEndTime().toInstant().toEpochMilli();
		final long startDate = context.getBar(startIndex).getEndTime().toInstant().toEpochMilli();
		final double x = startX + (state.getDate() - startDate) * (endX - startX) / (endDate - startDate);
		setCenterX(x);
	}
	
	private void updatePrice() {
		final double price = yAxis.getValue(getCenterY());
		state.setPrice(price);
	}
	
	private void onMouseDragged(MouseEvent event) {
		if(!event.isConsumed()) {
			setCenterX(event.getX());
			setCenterY(event.getY());
			event.consume();
		}
	}
	
	private void updateDate() {
		final int startIndex = 0;
		final int endIndex = context.getBarCount() - 1;
		final double endX = xAxis.getX(endIndex);
		final double startX = xAxis.getX(startIndex);
		final long endDate = context.getBar(endIndex).getEndTime().toInstant().toEpochMilli();
		final long startDate = context.getBar(startIndex).getEndTime().toInstant().toEpochMilli();
		final long date = startDate + (long) ((getCenterX() - startX) * (endDate - startDate) / (endX - startX));
		state.setDate(date);
	}
	
	@Override
	public Node getNode() {
		return this;
	}
	
	@Override
	public void moveTo(double x, double y) {
		setCenterX(x);
		setCenterY(y);
	}
	
	@Override
	public void dragTo(double x, double y) {
		moveTo(x, y);
	}
}
