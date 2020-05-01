package com.stox.module.screen;

import com.stox.fx.fluent.scene.control.FluentLabel;
import com.stox.fx.widget.Icon;

import javafx.scene.control.ListCell;

public class ScreenListCell extends ListCell<Screen<?>> {

	private FluentLabel graphic;
	
	@Override
	protected void updateItem(Screen<?> item, boolean empty) {
		super.updateItem(item, empty);
		if(null == item || empty){
			setText(null);
			setGraphic(null);
		}else{
			setText(item.name());
			if(null == graphic){
				graphic = new FluentLabel().focusTraversable(false).classes("icon", "tiny" , "inverted");
			}
			switch(item.screenType()){
			case BEARISH:
				graphic.classes("danger").getStyleClass().removeAll("primary", "success");
				graphic.setText(Icon.ARROW_DOWN);
				break;
			case BULLISH:
				graphic.classes("success").getStyleClass().removeAll("primary", "danger");
				graphic.setText(Icon.ARROW_UP);
				break;
			case NEUTRAL:
				graphic.classes("primary").getStyleClass().removeAll("success", "danger");
				graphic.setText(Icon.ARROW_UP);
			default:
				break;
			}
			setGraphic(graphic);
		}
	}
	

}
