package com.stox.module.charting.plot.price;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

import com.stox.fx.widget.Ui;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.event.DataChangedEvent;
import com.stox.module.charting.event.DataRequestEvent;
import com.stox.module.core.model.Bar;
import com.stox.module.core.model.BarSpan;
import com.stox.module.core.model.Scrip;
import com.stox.module.core.persistence.BarRepository;

import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DataRequestEventHandler implements EventHandler<DataRequestEvent> {

	private volatile long to;
	private volatile Scrip scrip;
	private volatile BarSpan barSpan = BarSpan.D;
	private volatile boolean loading, fullyLoaded;

	private final PricePlot pricePlot;
	private final BarRepository barRepository;
	private final ExecutorService executorService;

	@Override
	public void handle(DataRequestEvent event) {
		if (!equals(scrip, event.barSpan(), event.to())) {
			reset();
			this.scrip = pricePlot.scrip();
			this.barSpan = null == event.barSpan() ? barSpan : event.barSpan();
			this.to = event.to();
		}
		load(scrip, barSpan, to, event.xAxis());
	}

	private boolean equals(Scrip scrip, final BarSpan barSpan, final long to) {
		return Objects.equals(scrip, pricePlot.scrip()) && Objects.equals(barSpan, this.barSpan) && to == this.to;
	}

	private void reset() {
		loading = false;
		fullyLoaded = false;
		pricePlot.models().clear();
	}

	private void load(Scrip scrip, BarSpan barSpan, long to, XAxis xAxis) {
		if (shouldLoad(xAxis.getEndIndex()) && equals(scrip, barSpan, to)) {
			loading = true;
			executorService.submit(() -> doLoad(scrip, barSpan, to, xAxis));
		}
	}

	private void doLoad(Scrip scrip, BarSpan barSpan, long to, XAxis xAxis) {
		try {
			final List<Bar> bars = load(scrip, barSpan, to, xAxis.getUnitWidth());
			if (equals(scrip, barSpan, to)) {
				fullyLoaded = bars.isEmpty();
				if (!fullyLoaded) {
					pricePlot.models().addAll(bars);
					pricePlot.container().fireEvent(new DataChangedEvent(pricePlot.models()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (equals(scrip, barSpan, to))
				fullyLoaded = true;
			// TODO Toast or error message here
		} finally {
			if (equals(scrip, barSpan, to)) {
				loading = false;
				load(scrip, barSpan, to, xAxis);
			}
		}
	}

	private List<Bar> load(Scrip scrip, final BarSpan barSpan, final long to, final double unitWidth) {
		final List<Bar> models = pricePlot.models();
		final long effectiveTo = models.isEmpty() ? (0 >= to ? System.currentTimeMillis() : to) : models.get(models.size() - 1).date() - barSpan.millis();
		final int count = (int) Math.ceil((Ui.getParentOfType(Pane.class, pricePlot.container()).getWidth() / unitWidth));
		return barRepository.find(scrip.isin(), barSpan, effectiveTo, count);
	}

	private boolean shouldLoad(int endIndex) {
		return null != pricePlot.scrip() &&
				null != barSpan &&
				!loading &&
				!fullyLoaded &&
				endIndex > pricePlot.models().size();
	}

}
