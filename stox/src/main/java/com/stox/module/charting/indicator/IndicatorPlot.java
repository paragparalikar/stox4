package com.stox.module.charting.indicator;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.stox.fx.widget.FxMessageSource;
import com.stox.module.charting.Configuration;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;
import com.stox.module.charting.event.ConfigChangedEvent;
import com.stox.module.charting.event.UnderlayChangedEvent;
import com.stox.module.charting.indicator.addin.ChartAddIn;
import com.stox.module.charting.plot.DerivativePlot;
import com.stox.module.charting.plot.Underlay;
import com.stox.module.charting.plot.info.EditablePlotInfoPane;
import com.stox.module.charting.plot.info.PlotInfoPane;
import com.stox.module.charting.plot.info.ValuePlotInfoPane;
import com.stox.module.charting.unit.Unit;
import com.stox.module.charting.unit.parent.UnitParent;
import com.stox.module.core.model.Bar;

import lombok.NonNull;

public class IndicatorPlot<T, V, P> extends DerivativePlot<V> {

	private final T configuration;
	private final UnitParent<P> parent;
	private ValuePlotInfoPane valuePlotInfoPane;
	private final FxMessageSource messageSource;
	private EditablePlotInfoPane editablePlotInfoPane;
	private final ChartIndicator<T, V, P> indicator;
	private final List<ChartAddIn<V>> addIns = new ArrayList<>();
	private final NumberFormat numberFormat = NumberFormat.getInstance();
	
	private Underlay oldUnderlay;

	public IndicatorPlot(
			@NonNull final FxMessageSource messageSource,
			@NonNull final Configuration configuration, 
			@NonNull final ChartIndicator<T, V, P> indicator) {
		super(configuration);
		this.indicator = indicator;
		this.parent = indicator.parent(container());
		this.messageSource = messageSource;
		this.configuration = indicator.defaultConfig();
		build();
	}

	private void build() {
		addIns.addAll(indicator.addIns(configuration, parent));
		addIns.forEach(addIn -> container().getChildren().add(addIn.getNode()));
		addIns.forEach(ChartAddIn::update);
		parent.bindColorProperty(colorProperty());
		valuePlotInfoPane.setName(indicator.name());
		valuePlotInfoPane.bind(colorProperty());
		editablePlotInfoPane.addEditEventHandler(event -> edit());
		numberFormat.setMaximumFractionDigits(2);
	}

	private void edit() {
		oldUnderlay = underlay();
		new IndicatorEditorModal(messageSource, configuration, indicator, this::configurationChanged).show(container());
	}
	
	private void configurationChanged(final Object configuration) {
		container().fireEvent(oldUnderlay.equals(underlay()) ? new ConfigChangedEvent(this) : new UnderlayChangedEvent(this));
	}

	@Override
	public Underlay underlay() {
		return indicator.underlay(configuration);
	}

	@Override
	public IndicatorPlot<T, V, P> layoutChartChildren(XAxis xAxis, YAxis yAxis) {
		parent.preLayoutChartChildren(xAxis, yAxis);
		addIns.forEach(addIn -> addIn.preLayoutChartChildren(xAxis, yAxis));
		indicator.layoutChartChildren(xAxis, yAxis, models(), units(), parent, this);
		parent.postLayoutChartChildren(xAxis, yAxis);
		addIns.forEach(addIn -> addIn.postLayoutChartChildren(xAxis, yAxis));
		return this;
	}

	@Override
	protected IndicatorPlot<T, V, P> layoutUnit(Unit<V> unit, int index, V model, V previousModel, XAxis xAxis, YAxis yAxis) {
		super.layoutUnit(unit, index, model, previousModel, xAxis, yAxis);
		addIns.forEach(addIn -> addIn.layoutUnit(model, unit, xAxis, yAxis));
		return this;
	}

	void doLayoutChartChildren(XAxis xAxis, YAxis yAxis) {
		super.layoutChartChildren(xAxis, yAxis);
	}

	@Override
	public void load(List<Bar> bars) {
		final List<V> models = models();
		synchronized (models) {
			models.clear();
			final List<V> values = indicator.computeAll(Collections.emptyList(), bars, configuration);
			models.addAll(values);
		}
	}

	@Override
	public Unit<V> unit() {
		return indicator.unit(parent);
	}

	@Override
	protected PlotInfoPane buildPlotInfoPane() {
		if (null == valuePlotInfoPane) {
			final PlotInfoPane plotInfoPane = super.buildPlotInfoPane();
			editablePlotInfoPane = new EditablePlotInfoPane(plotInfoPane);
			valuePlotInfoPane = new ValuePlotInfoPane(editablePlotInfoPane);
		}
		return valuePlotInfoPane;
	}

	@Override
	public void showIndexInfo(int index) {
		final V value = 0 <= index && index < models().size() ? models().get(index) : null;
		valuePlotInfoPane.setValue(null == value ? "" : (value instanceof Number ? numberFormat.format(value) : value.toString()));
	}

	@Override
	public double min() {
		return Underlay.NONE.equals(underlay()) ? super.min() : Double.MAX_VALUE;
	}

	@Override
	public double max() {
		return Underlay.NONE.equals(underlay()) ? super.max() : Double.MIN_VALUE;
	}

	@Override
	public double min(V model) {
		return indicator.min(model);
	}

	@Override
	public double max(V model) {
		return indicator.max(model);
	}

}
