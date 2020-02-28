package com.stox.module.charting.chart;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

import com.stox.module.charting.Configuration;
import com.stox.module.charting.axis.horizontal.DateTimeAxis;
import com.stox.module.charting.axis.horizontal.MutableXAxis;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.DelegatingYAxis;
import com.stox.module.charting.axis.vertical.PrimaryValueAxis;
import com.stox.module.charting.drawing.DrawingRepository;
import com.stox.module.charting.event.DataRequestEvent;
import com.stox.module.charting.grid.HorizontalGrid;
import com.stox.module.charting.grid.VerticalGrid;
import com.stox.module.charting.plot.price.DataRequestEventHandler;
import com.stox.module.charting.plot.price.PrimaryPricePlot;
import com.stox.module.charting.unit.PriceUnitType;
import com.stox.module.charting.widget.BarInfoPanel;
import com.stox.module.core.model.Bar;
import com.stox.module.core.model.BarSpan;
import com.stox.module.core.model.Scrip;
import com.stox.module.core.persistence.BarRepository;

import lombok.Builder;
import lombok.NonNull;

public class PrimaryChart extends Chart {

	private final MutableXAxis xAxis;
	private final DateTimeAxis dateTimeAxis;
	private final BarRepository barRepository;
	private final ExecutorService executorService;
	private final PrimaryPricePlot primaryPricePlot;
	private final DrawingRepository drawingRepository;
	private final HorizontalGrid horizontalGrid = new HorizontalGrid();

	@Builder
	public PrimaryChart(@NonNull final Configuration configuration, @NonNull final MutableXAxis xAxis, @NonNull final DelegatingYAxis volumeYAxis,
			@NonNull final VerticalGrid verticalGrid, @NonNull final BarInfoPanel barInfoPanel, @NonNull final ExecutorService executorService,
			@NonNull final BarRepository barRepository, @NonNull final DrawingRepository drawingRepository) {
		super(xAxis, configuration, volumeYAxis);
		this.xAxis = xAxis;
		this.barRepository = barRepository;
		this.executorService = executorService;
		this.drawingRepository = drawingRepository;

		valueAxis(new PrimaryValueAxis(horizontalGrid));
		container().bottom((dateTimeAxis = new DateTimeAxis(verticalGrid)));
		plotInfoContainer().getChildren().add(barInfoPanel);
		primaryPricePlot = new PrimaryPricePlot(barInfoPanel, configuration);
		content().getChildren().add(primaryPricePlot.container());
		primaryPricePlot.container().toFront();
		bind();
	}

	public void unitType(final PriceUnitType unitType) {
		primaryPricePlot.setPriceUnitType(unitType);
	}

	public PriceUnitType unitType() {
		return primaryPricePlot.priceUnitType();
	}

	public void load(final long to, final BarSpan barSpan, final XAxis xAxis) {
		primaryPricePlot.container().fireEvent(new DataRequestEvent(to, barSpan, xAxis));
	}
		
	@Override
	public Chart unload(Scrip scrip) {
		Optional.ofNullable(scrip).ifPresent(s -> drawingRepository.persist(scrip.getIsin(), drawings()));
		return super.unload(scrip);
	}

	@Override
	public PrimaryChart showIndexInfo(int index) {
		super.showIndexInfo(index);
		primaryPricePlot.showIndexInfo(index);
		return this;
	}

	@Override
	protected PrimaryChart bind() {
		super.bind();
		content().widthProperty().addListener((o, old, value) -> xAxis.setWidth(value.doubleValue()));
		primaryPricePlot.container().addEventHandler(DataRequestEvent.TYPE, new DataRequestEventHandler(primaryPricePlot, barRepository, executorService));
		return this;
	}

	public Scrip scrip() {
		return primaryPricePlot.scrip();
	}

	public PrimaryChart scrip(final Scrip scrip) {
		primaryPricePlot.scrip(scrip);
		Optional.ofNullable(scrip).ifPresent(s -> drawingRepository.find(scrip.getIsin()).forEach(this::add));
		return this;
	}

	@Override
	public PrimaryChart reset() {
		primaryPricePlot.reset();
		super.reset();
		return this;
	}

	@Override
	public PrimaryChart updateValueBounds() {
		primaryPricePlot.updateValueBounds(xAxis.getClippedStartIndex(), xAxis.getClippedEndIndex());
		super.updateValueBounds();
		yAxis().setMin(Math.min(yAxis().getMin(), primaryPricePlot.min()));
		yAxis().setMax(Math.max(yAxis().getMax(), primaryPricePlot.max()));
		return this;
	}

	@Override
	public PrimaryChart layoutChartChildren() {
		primaryPricePlot.layoutChartChildren(xAxis, yAxis());
		super.layoutChartChildren();
		dateTimeAxis.layoutChartChildren(xAxis, bars());
		return this;
	}

	public List<Bar> bars() {
		return primaryPricePlot.models();
	}

}
