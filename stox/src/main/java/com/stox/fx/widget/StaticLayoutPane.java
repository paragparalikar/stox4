package com.stox.fx.widget;

import javafx.scene.Node;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaticLayoutPane extends NoLayoutPane {

	private double labelHeight;
	private double labelWidth;
	private double paddingLeft;
	private double paddingRight;
	private double paddingTop;
	private double paddingBottom;

	public void add(double x, double y, Node node) {
		getChildren().add(node);
		node.resizeRelocate(x + paddingLeft, y + paddingTop, getLabelWidth() - paddingLeft - paddingRight,
				getLabelHeight() - paddingTop - paddingBottom);
	}

	public double getLabelWidth() {
		return 0 == labelWidth ? getWidth() : labelWidth;
	}

	public double getLabelHeight() {
		return 0 == labelHeight ? getHeight() : labelHeight;
	}

	public void setPadding(double left, double top, double right, double bottom) {
		paddingLeft = left;
		paddingTop = top;
		paddingRight = right;
		paddingBottom = bottom;
	}

}
