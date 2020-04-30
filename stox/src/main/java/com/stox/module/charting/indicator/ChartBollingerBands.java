package com.stox.module.charting.indicator;

import java.util.Collections;
import java.util.List;

import com.stox.fx.widget.parent.Parent;
import com.stox.module.charting.indicator.addin.ChartAddIn;
import com.stox.module.charting.indicator.unit.ChannelUnit;
import com.stox.module.charting.indicator.unit.parent.ChannelUnitParent;
import com.stox.module.charting.plot.Underlay;
import com.stox.module.charting.unit.Unit;
import com.stox.module.charting.unit.parent.UnitParent;
import com.stox.module.indicator.BollingerBands;
import com.stox.module.indicator.BollingerBands.Config;
import com.stox.module.indicator.Indicator;
import com.stox.module.indicator.model.Channel;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import lombok.experimental.Delegate;

public class ChartBollingerBands extends AbstractChartIndicator<Config, Channel, Point2D>{

	@Delegate
	private final Indicator<Config, Channel> bollingerBandsIndicator = Indicator.ofType(BollingerBands.class);
	
	@Override
	public String name() {
		return "Bollinger Bands";
	}

	@Override
	public UnitParent<Point2D> parent(Group group) {
		return new ChannelUnitParent(group);
	}

	@Override
	public Unit<Channel> unit(Parent<Point2D> parent) {
		return new ChannelUnit(parent);
	}

	@Override
	public boolean groupable() {
		return false;
	}

	@Override
	public Underlay underlay(Config config) {
		return Underlay.PRICE;
	}

	@Override
	public List<ChartAddIn<Channel>> addIns(Config config, UnitParent<Point2D> parent) {
		return Collections.emptyList();
	}

	@Override
	public double min(Channel value) {
		return null == value ? Double.MAX_VALUE : value.lower();
	}

	@Override
	public double max(Channel value) {
		return null == value ? Double.MIN_VALUE : value.upper();
	}
	
}
