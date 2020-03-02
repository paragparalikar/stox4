package com.stox.module.charting.plot.price;

import java.util.List;

import com.stox.module.charting.Configuration;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;
import com.stox.module.charting.event.DataChangedEvent;
import com.stox.module.charting.plot.info.SimplePlotInfoPane;
import com.stox.module.charting.unit.PriceUnitFactory;
import com.stox.module.charting.unit.PriceUnitType;
import com.stox.module.charting.unit.Unit;
import com.stox.module.charting.unit.parent.GroupUnitParent;
import com.stox.module.charting.unit.parent.UnitParent;
import com.stox.module.charting.widget.BarInfoPanel;
import com.stox.module.core.model.Bar;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class PrimaryPricePlot extends PricePlot<PrimaryPricePlotState> {

	private UnitParent<?> parent;
	@Getter private PriceUnitType priceUnitType;
	private final BarInfoPanel barInfoPanel;
	private final PriceUnitFactory priceUnitFactory;

	public PrimaryPricePlot(final BarInfoPanel barInfoPanel, final Configuration configuration) {
		super(configuration);
		priceUnitType = PriceUnitType.CANDLE;
		this.barInfoPanel = barInfoPanel;
		parent = new GroupUnitParent(container());
		priceUnitFactory = new PriceUnitFactory();
	}
	
	@Override
	protected PrimaryPricePlotState fill(PrimaryPricePlotState state) {
		return super.fill(state).priceUnitType(priceUnitType);
	}
	
	@Override
	public PrimaryPricePlotState state() {
		return fill(new PrimaryPricePlotState());
	}
	
	@Override
	public void state(PrimaryPricePlotState state) {
		super.state(state);
		setPriceUnitType(state.priceUnitType());
	}

	@Override
	protected SimplePlotInfoPane buildPlotInfoPane() {
		return null;
	}

	@Override
	public void showIndexInfo(int index) {
		barInfoPanel.set(index >= 0 && index < models().size() ? models().get(index) : null);
	}

	@Override
	public Unit<Bar> unit() {
		return priceUnitFactory.build(priceUnitType, parent, configuration());
	}

	@Override
	public PrimaryPricePlot layoutChartChildren(XAxis xAxis, YAxis yAxis) {
		parent.preLayoutChartChildren(xAxis, yAxis);
		super.layoutChartChildren(xAxis, yAxis);
		parent.postLayoutChartChildren(xAxis, yAxis);
		return this;
	}

	public void setPriceUnitType(@NonNull final PriceUnitType priceUnitType) {
		if (!priceUnitType.equals(this.priceUnitType)) {
			this.priceUnitType = priceUnitType;
			updateParent();
			units().forEach(Unit::detach);
			units().clear();
			final List<Bar> bars = models();
			synchronized(bars){
				container().fireEvent(new DataChangedEvent(bars));
			}
		}
	}

	private void updateParent() {
		parent.unbindColorProperty();
		container().getChildren().remove(parent.getNode());
		parent = priceUnitFactory.buildParent(priceUnitType, parent, container());
		parent.bindColorProperty(colorProperty());
		if (container() != parent.getNode()) {
			container().getChildren().add(parent.getNode());
		}
	}

}
