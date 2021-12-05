package com.stox.module.screener.modal;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import com.stox.fx.fluent.scene.control.FluentLabel;
import com.stox.fx.fluent.scene.control.FluentListView;
import com.stox.fx.fluent.scene.control.FluentTab;
import com.stox.fx.fluent.scene.control.FluentTabPane;
import com.stox.fx.fluent.scene.control.FluentTextField;
import com.stox.fx.fluent.scene.layout.FluentBorderPane;
import com.stox.fx.fluent.scene.layout.FluentHBox;
import com.stox.fx.fluent.scene.layout.FluentVBox;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Icon;
import com.stox.fx.widget.auto.AutoUiBuilder;
import com.stox.fx.widget.auto.AutoVBox;
import com.stox.module.core.widget.supplier.scrip.ScripsSupplierView;
import com.stox.module.screen.Screen;
import com.stox.module.screen.ScreenListCell;
import com.stox.module.screener.ScreenerConfig;
import com.stox.module.screener.ScreenerService;
import com.stox.workbench.modal.ActionModal;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ScreenerConfigEditorModal extends ActionModal<ScreenerConfigEditorModal> {

	@NonNull private final FxMessageSource messageSource;
	@NonNull private final ScreenerService screenerService;
	@NonNull private final Collection<Supplier<ScripsSupplierView>> scripsSupplierViewSuppliers;
	
	private AutoVBox autoUi;
	private Screen<?> screen;
	private Object screenConfig;
	
	private final AutoUiBuilder autoUiBuilder = new AutoUiBuilder();
	private final FluentTextField spanTextField = new FluentTextField();
	private final FluentTextField offsetTextField = new FluentTextField();
	private final FluentTabPane scripsProviderTabPane = new FluentTabPane();
	private final FluentVBox configContainer = new FluentVBox(
			new FluentHBox(new FluentLabel("Span"), spanTextField),
			new FluentHBox(new FluentLabel("Offset"), offsetTextField),
			scripsProviderTabPane);
	private final FluentListView<Screen<?>> screensListView = new FluentListView<Screen<?>>(FXCollections.observableList(Screen.ALL))
			.singleSelectionMode();
	private final FluentBorderPane container = new FluentBorderPane(configContainer, null, null, null, screensListView)
			.classes("padded", "content", "screener-modal");
	
	@Override
	public ScreenerConfigEditorModal show(Node caller) {
		build();
		return super.show(caller);
	}
	
	private void build() {
		if(scripsProviderTabPane.getTabs().isEmpty()) {
			graphic(Icon.ALIGN_RIGHT)
				.title(messageSource.get("Modify Screener Configuration"))
				.content(container)
				.actionButtonText(messageSource.get("Run"))
				.cancelButtonText(messageSource.get("Cancel"));
			final List<ScripsSupplierView> scripsSupplierViews = screenerService
					.getScreenerConfig().getScripsSupplierViews();
			scripsSupplierViewSuppliers.stream()
				.map(Supplier::get)
				.map(view -> {scripsSupplierViews.add(view); return view;})
				.map(view -> new FluentTab(view.name(), view.getNode()))
				.forEach(scripsProviderTabPane.getTabs()::add);
			
			screensListView.setCellFactory(listView -> new ScreenListCell());
			BorderPane.setMargin(screensListView, new Insets(0, 20, 0, 0));
			screensListView.getSelectionModel().selectedItemProperty().addListener(this::screenChanged);
			
			final ScreenerConfig screenerConfig  = screenerService.getScreenerConfig();
			spanTextField.setText(String.valueOf(screenerConfig.getSpan()));
			offsetTextField.setText(String.valueOf(screenerConfig.getOffset()));
		}
	}
	
	private void screenChanged(ObservableValue<? extends Screen<?>> observable, Screen<?> oldValue, Screen<?> newValue) {
		if(null != autoUi) {
			configContainer.getChildren().remove(autoUi);
		}
		
		if(null != newValue) {
			this.screen = newValue;
			screenConfig = newValue.defaultConfig();
			autoUi = autoUiBuilder.build(screenConfig);
			configContainer.getChildren().add(autoUi);
			autoUi.populateView();
		}
	}
	
	@Override
	protected void action() {
		if(null != autoUi) {
			autoUi.populateModel();
			final ScreenerConfig screenerConfig  = screenerService.getScreenerConfig();
			screenerConfig.setOffset(Integer.parseInt(offsetTextField.getText()));
			screenerConfig.setSpan(Integer.parseInt(spanTextField.getText()));
			screenerConfig.setScreen(screen);
			screenerConfig.setScreenConfig(screenConfig);
			screenerService.restart();
			super.hide();
		}
	}

	@Override
	protected ScreenerConfigEditorModal getThis() {
		return this;
	}

}
