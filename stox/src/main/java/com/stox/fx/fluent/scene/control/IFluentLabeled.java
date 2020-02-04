package com.stox.fx.fluent.scene.control;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Labeled;
import javafx.scene.control.OverrunStyle;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public interface IFluentLabeled<T extends Labeled & IFluentLabeled<T>> extends IFluentControl<T> {

	default Font font() {
		return getThis().getFont();
	}

	default T font(Font value) {
		getThis().setFont(value);
		return getThis();
	}

	default String text() {
		return getThis().getText();
	}

	default T text(String value) {
		getThis().setText(value);
		return getThis();
	}

	default T alignment(Pos value) {
		getThis().setAlignment(value);
		return getThis();
	}

	default T textAlignment(TextAlignment value) {
		getThis().setTextAlignment(value);
		return getThis();
	}

	default T textOverrun(OverrunStyle value) {
		getThis().setTextOverrun(value);
		return getThis();
	}

	default T ellipsisString(String value) {
		getThis().setEllipsisString(value);
		return getThis();
	}

	default T wrapText(boolean value) {
		getThis().wrapText(value);
		return getThis();
	}

	default T graphic(Node value) {
		getThis().setGraphic(value);
		return getThis();
	}

	default T contentDisplay(ContentDisplay value) {
		getThis().setContentDisplay(value);
		return getThis();
	}

	default T graphicTextGap(double value) {
		getThis().setGraphicTextGap(value);
		return getThis();
	}

	default T textFill(Paint value) {
		getThis().setTextFill(value);
		return getThis();
	}
}
