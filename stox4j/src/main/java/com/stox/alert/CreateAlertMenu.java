package com.stox.alert;

import org.greenrobot.eventbus.EventBus;
import org.ta4j.core.Bar;

import com.stox.alert.event.AlertCreatedEvent;
import com.stox.common.event.SelectedBarQueryEvent;
import com.stox.common.scrip.Scrip;
import com.stox.common.ui.Fx;
import com.stox.common.ui.Icon;
import com.stox.common.ui.modal.Modal;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class CreateAlertMenu extends MenuItem implements EventHandler<ActionEvent> {

	private final EventBus eventBus;
	private final AlertService alertService;
	
	public CreateAlertMenu(EventBus eventBus, AlertService alertService) {
		this.eventBus = eventBus;
		this.alertService = alertService;
		setText("Create alert");
		setOnAction(this);
	}

	@Override
	public void handle(ActionEvent event) {
		final double screenX = getParentPopup().getX();
		final double screenY = getParentPopup().getY();
		final SelectedBarQueryEvent selectedBarQueryEvent = new SelectedBarQueryEvent(screenX, screenY);
		eventBus.post(selectedBarQueryEvent);
		final Bar bar = selectedBarQueryEvent.getBar();
		final Scrip scrip = selectedBarQueryEvent.getScrip();
		create(scrip, bar);
	}
	
	private void create(Scrip scrip, Bar bar) {
		final TextField textField = new TextField();
		textField.setMaxWidth(Double.MAX_VALUE);
		textField.getStyleClass().add("large");
		textField.setPromptText("Price");
		textField.setText(bar.getClosePrice().toString());
		final Label graphics = new Label(Icon.PLUS);
		graphics.getStyleClass().add("icon");
		final Button button = new Button("Create", graphics);
		final Modal modal = new Modal()
			.withTitleIcon(Icon.BOOKMARK)
			.withTitleText("Create New Alert")
			.withContent(textField)
			.withButton(button)
			.show(this.getParentPopup().getOwnerNode());
		button.setOnAction(event -> create(textField.getText(), scrip, modal));
		button.setDefaultButton(true);
		Fx.run(textField::requestFocus);
	}

	private Object create(String text, Scrip scrip, Modal modal) {
		final double price = Double.parseDouble(text);
		final Alert alert = Alert.builder().isin(scrip.getIsin()).price(price).build();
		Thread.ofVirtual().start(() -> {
			alertService.save(alert);
			eventBus.post(new AlertCreatedEvent(alert));
			modal.hide();
		});
		return alert;
	}
	
}
