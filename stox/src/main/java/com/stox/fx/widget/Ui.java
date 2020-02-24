package com.stox.fx.widget;

import java.util.List;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import lombok.NonNull;

public class Ui {
	
	public static void fx(Runnable runnable) {
		if (Platform.isFxApplicationThread()) {
			runnable.run();
		} else {
			Platform.runLater(runnable);
		}
	}
	
	public static String web(final Color color) {
		return String.format("#%02X%02X%02X", (int) (color.getRed() * 255), (int) (color.getGreen() * 255),
				(int) (color.getBlue() * 255));
	}
	
	public static double px(final double value) {
		return Math.floor(value) + 0.5;
	}
	
	public static <T extends Parent> T box(T parent){
		parent.getStyleClass().add("box");
		final List<Node> children = parent.getChildrenUnmodifiable();
		for(int index = 0; index < children.size(); index++){
			final Node child = children.get(index);
			if(0 == index){
				child.getStyleClass().add("first");
			}else if(index == children.size() - 1){
				child.getStyleClass().add("last");
			}else{
				child.getStyleClass().add("middle");
			}
		}
		return parent;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getParentOfType(final Class<T> type, final Node node) {
		final javafx.scene.Parent parent = null == node ? null : node.getParent();
		return null == parent ? null
				: type.isAssignableFrom(parent.getClass()) ? (T) parent : getParentOfType(type, parent);
	}

	public static Tooltip tooltip(@NonNull final ObservableValue<String> textValue) {
		final Tooltip tooltip = new Tooltip();
		tooltip.textProperty().bind(textValue);
		return tooltip;
	}
	
}
