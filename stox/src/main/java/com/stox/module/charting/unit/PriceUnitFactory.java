package com.stox.module.charting.unit;

import com.stox.fx.widget.parent.Parent;
import com.stox.module.charting.Configuration;
import com.stox.module.charting.unit.parent.GroupUnitParent;
import com.stox.module.charting.unit.parent.PolygonUnitParent;
import com.stox.module.charting.unit.parent.PolylineUnitParent;
import com.stox.module.charting.unit.parent.UnitParent;

import javafx.scene.Group;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;

public class PriceUnitFactory {

	public UnitParent<?> buildParent(final PriceUnitType unitType, final UnitParent<?> currentParent,
			final Group group) {
		switch (unitType) {
		case CANDLE:
		case HLC:
		case OHLC:
			return currentParent instanceof GroupUnitParent ? currentParent : new GroupUnitParent(group);
		case LINE:
			return currentParent instanceof PolylineUnitParent ? currentParent : new PolylineUnitParent(new Polyline());
		case AREA:
			return currentParent instanceof PolygonUnitParent ? currentParent : new PolygonUnitParent(new Polygon());
		default:
			return currentParent;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PriceUnit build(final PriceUnitType unitType, final Parent parent, final Configuration configuration) {
		switch (unitType) {
		case AREA:
			return new AreaPriceUnit(parent, configuration);
		case CANDLE:
			return new CandlePriceUnit(parent, configuration);
		case HLC:
			return new HlcPriceUnit(parent, configuration);
		case LINE:
			return new LinePriceUnit(parent, configuration);
		case OHLC:
			return new OhlcPriceUnit(parent, configuration);
		default:
			return new CandlePriceUnit(parent, configuration);
		}
	}

}
