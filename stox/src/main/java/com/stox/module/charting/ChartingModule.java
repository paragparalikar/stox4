package com.stox.module.charting;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import com.stox.Context;
import com.stox.fx.widget.Icon;
import com.stox.module.charting.drawing.DrawingState;
import com.stox.module.charting.drawing.DrawingStateRepository;
import com.stox.module.charting.drawing.region.ChartRegionState;
import com.stox.module.charting.drawing.segment.horizontal.HorizontalSegmentState;
import com.stox.module.charting.drawing.segment.trend.TrendSegmentState;
import com.stox.module.charting.drawing.segment.vertical.VerticalSegmentState;
import com.stox.module.charting.drawing.text.ChartTextState;
import com.stox.util.JsonConverter;
import com.stox.workbench.module.ModuleView;
import com.stox.workbench.module.UiModule;

import javafx.beans.value.ObservableValue;
import lombok.NonNull;

public class ChartingModule extends UiModule<ChartingViewState> {

	private final TypeAdapterFactory drawingStateTypeAdapterFactory = RuntimeTypeAdapterFactory.of(DrawingState.class)
			.registerSubtype(TrendSegmentState.class, TrendSegmentState.TYPE)
			.registerSubtype(HorizontalSegmentState.class, HorizontalSegmentState.TYPE)
			.registerSubtype(VerticalSegmentState.class, VerticalSegmentState.TYPE)
			.registerSubtype(ChartRegionState.class, ChartRegionState.TYPE)
			.registerSubtype(ChartTextState.class, ChartTextState.TYPE);
	private final Gson gson = new GsonBuilder()
			.registerTypeAdapterFactory(drawingStateTypeAdapterFactory)
			.create();
	private final JsonConverter jsonConverter = new JsonConverter(gson);
	private final DrawingStateRepository drawingStateRepository;

	public ChartingModule(@NonNull final Context context) {
		super(context);
		drawingStateRepository = new DrawingStateRepository(context.getConfig().getHome(), jsonConverter);
	}

	@Override
	protected String getIcon() {
		return Icon.LINE_CHART;
	}

	@Override
	protected String getCode() {
		return "charting";
	}

	@Override
	protected ObservableValue<String> getModuleName() {
		return getContext().getMessageSource().get("Chart");
	}

	@Override
	protected ModuleView<ChartingViewState> buildModuleView() {
		final Context context = getContext();
		final ChartingView chartingView = new ChartingView(
				context.getScheduledExecutorService(), 
				context.getMessageSource(), 
				context.getBarRepository(), 
				context.getScripRepository(),
				drawingStateRepository);
		context.getContextMenuConfigurers().forEach(configurer -> configurer.accept(chartingView.getContextMenu(), chartingView));
		return chartingView;
	}

}
