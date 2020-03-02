package com.stox.module.charting.plot;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.stox.fx.widget.parent.GroupParentAdapter;
import com.stox.fx.widget.parent.Parent;
import com.stox.module.charting.Configuration;
import com.stox.module.charting.axis.vertical.TransformerYAxis;
import com.stox.module.charting.plot.info.EditablePlotInfoPane;
import com.stox.module.charting.plot.info.PlotInfoPane;
import com.stox.module.charting.plot.info.ValuePlotInfoPane;
import com.stox.module.charting.unit.BarUnit;
import com.stox.module.charting.unit.Unit;
import com.stox.module.core.model.Bar;
import com.stox.util.StringUtil;

import javafx.scene.Node;

public class VolumePlot extends DerivativePlot<Double> {

	private ValuePlotInfoPane valuePlotInfoPane;
	private final TransformerYAxis transformerYAxis;
	private final Parent<Node> parent = new GroupParentAdapter(container());

	public VolumePlot(final Configuration configuration, final TransformerYAxis yAxis) {
		super(configuration);
		transformerYAxis = yAxis;
	}

	@Override
	public void showIndexInfo(int index) {
		final List<Double> values = models();
		valuePlotInfoPane.setValue(index >= 0 && index < values.size() ? StringUtil.stringValueOf(values.get(index)) : null);
	}

	@Override
	protected PlotInfoPane buildPlotInfoPane() {
		return Optional.ofNullable(valuePlotInfoPane)
				.orElseGet(() -> valuePlotInfoPane = new ValuePlotInfoPane(new EditablePlotInfoPane(super.buildPlotInfoPane().setName("Volume"))));
	}

	@Override
	public VolumePlot updateValueBounds(int start, int end) {
		super.updateValueBounds(start, end);
		transformerYAxis.setMin(super.min());
		transformerYAxis.setMax(super.max());
		return this;
	}

	@Override
	public Unit<Double> unit() {
		final BarUnit barUnit = new BarUnit(parent);
		barUnit.setOpacity(0.25);
		return barUnit;
	}

	@Override
	public void load(List<Bar> bars) {
		final List<Double> models = models();
		synchronized (models) {
			models.clear();
			models.addAll(bars.stream().map(bar -> bar.getVolume()).collect(Collectors.toList()));
		}
	}

	@Override
	public double min(Double model) {
		return model;
	}

	@Override
	public double max(Double model) {
		return model;
	}

	@Override
	public double min() {
		return Double.MAX_VALUE;
	}

	@Override
	public double max() {
		return Double.MIN_VALUE;
	}

	@Override
	public Underlay underlay() {
		return Underlay.VOLUME;
	}

}
