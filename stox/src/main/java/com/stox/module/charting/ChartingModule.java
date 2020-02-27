package com.stox.module.charting;

import com.stox.Context;
import com.stox.fx.widget.Icon;
import com.stox.module.charting.drawing.DrawingRepository;
import com.stox.workbench.module.ModuleView;
import com.stox.workbench.module.UiModule;

import javafx.beans.value.ObservableValue;
import lombok.NonNull;

public class ChartingModule extends UiModule<ChartingViewState> {
	
	private final DrawingRepository drawingRepository;

	public ChartingModule(@NonNull final Context context) {
		super(context);
		drawingRepository = new DrawingRepository(context.getConfig().getHome());
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
		return new ChartingView(context.getScheduledExecutorService(), context.getMessageSource(), context.getBarRepository(), drawingRepository);
	}

}
