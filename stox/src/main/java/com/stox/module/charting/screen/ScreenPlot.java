package com.stox.module.charting.screen;

import java.util.ArrayList;
import java.util.List;

import com.stox.module.charting.Configuration;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;
import com.stox.module.charting.plot.DerivativePlot;
import com.stox.module.charting.plot.Underlay;
import com.stox.module.charting.plot.info.EditablePlotInfoPane;
import com.stox.module.charting.plot.info.PlotInfoPane;
import com.stox.module.charting.screen.unit.ScreenUnitFactory;
import com.stox.module.charting.unit.Unit;
import com.stox.module.core.model.Bar;
import com.stox.module.screen.Screen;

import lombok.NonNull;

public class ScreenPlot<T> extends DerivativePlot<ScreenMatch> {

	private final Screen<T> screen;
	private final T configuration;
	private EditablePlotInfoPane editablePlotInfoPane;

	public ScreenPlot(@NonNull final Screen<T> screen, final Configuration chartConfiguration) {
		super(chartConfiguration);
		this.screen = screen;
		configuration = screen.defaultConfig();
		plotInfoPane().setName(screen.name());
		editablePlotInfoPane.addEditEventHandler(event -> edit());
	}

	private void edit() {/*
		final ModalDialogView view = new ModalDialogView();
		view.setTitleIcon(Icon.FILTER);
		view.setTitleText("Edit " + screen.getName());
		view.setPseudoClassState(UiConstant.PSEUDO_CLASS_PRIMARY, true);
		final AutoView autoView = new AutoView(configuration);
		view.setContent(autoView);
		view.setActionButtonText("Edit");
		final ModalDialogPresenter presenter = new ModalDialogPresenter(view);
		view.addActionEventHandler(event -> {
			autoView.updateModel();
			presenter.hide();
			getNode().fireEvent(new ConfigChangedEvent(this));
		});

		presenter.show();
		presenter.center();
	*/}

	@Override
	public Underlay underlay() {
		return Underlay.PRICE;
	}

	@Override
	public void load(final List<Bar> bars) {
		final List<ScreenMatch> models = models();
		models.clear();
		final int minBarCount = screen.minBarCount(configuration);
		if (minBarCount + 1 < bars.size()) {
			for (int index = bars.size() - minBarCount - 1; index >= 0; index--) {
				if (screen.match(bars.subList(index, index + minBarCount + 1), configuration)) {
					models.add(0, new ScreenMatch(bars.get(index), index));
				}
			}
		}
	}

	@Override
	public void showIndexInfo(int index) {

	}

	@Override
	public Unit<ScreenMatch> unit() {
		return ScreenUnitFactory.getInstance().build(screen.screenType(), container());
	}

	private final List<ScreenMatch> visibleModels = new ArrayList<>();

	@Override
	public ScreenPlot<T> updateValueBounds(int start, int end) {
		return this;
	}

	@Override
	public ScreenPlot<T> layoutChartChildren(XAxis xAxis, YAxis yAxis) {
		populateVisibleModels(xAxis);
		ensureUnitsSize(0, visibleModels.size() - 1);
		for (int index = 0; index < visibleModels.size(); index++) {
			final ScreenMatch model = visibleModels.get(index);
			final Unit<ScreenMatch> unit = units().get(index);
			unit.update(index, model, null, xAxis, yAxis);
		}
		return this;
	}

	private void populateVisibleModels(XAxis xAxis) {
		visibleModels.clear();
		final List<ScreenMatch> models = models();
		final int layoutEndIndex = Math.max(0, xAxis.getEndIndex());
		final int layoutStartIndex = Math.max(0, xAxis.getStartIndex());
		for (int index = 0; index < models.size(); index++) {
			final ScreenMatch model = models.get(index);
			final int modelIndex = model.index();
			if (modelIndex >= layoutStartIndex && modelIndex <= layoutEndIndex) {
				visibleModels.add(model);
			}
		}
	}

	@Override
	public double min(ScreenMatch model) {
		return Double.MAX_VALUE;
	}

	@Override
	public double max(ScreenMatch model) {
		return Double.MIN_VALUE;
	}

	@Override
	protected PlotInfoPane buildPlotInfoPane() {
		if (null == editablePlotInfoPane) {
			final PlotInfoPane plotInfoPane = super.buildPlotInfoPane();
			editablePlotInfoPane = new EditablePlotInfoPane(plotInfoPane);
		}
		return editablePlotInfoPane;
	}

}
