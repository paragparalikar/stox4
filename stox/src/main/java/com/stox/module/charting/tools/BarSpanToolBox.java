package com.stox.module.charting.tools;

import java.util.function.Consumer;

import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Ui;
import com.stox.module.charting.ChartingView;
import com.stox.module.core.model.BarSpan;
import com.stox.workbench.link.Link;
import com.stox.workbench.link.Link.State;
import com.stox.workbench.link.LinkButton;

import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import lombok.NonNull;

public class BarSpanToolBox extends HBox implements ChartingToolBox {

	private ChartingView chartingView;
	private final ToggleGroup toggleGroup = new ToggleGroup();
	private final Consumer<State> stateConsumer = this::linkStateChanged;

	public BarSpanToolBox(@NonNull final FxMessageSource messageSource) {
		for (BarSpan barSpan : BarSpan.values()) {
			final RadioButton radioButton = new RadioButton(barSpan.getShortName());
			radioButton.setUserData(barSpan);
			radioButton.setTooltip(Ui.tooltip(messageSource.get(barSpan.getName())));
			radioButton.getStyleClass().remove("radio-button");
			radioButton.getStyleClass().addAll("toggle-button", "primary","small");
			toggleGroup.getToggles().add(radioButton);
			getChildren().add(radioButton);
			radioButton.setMinWidth(Region.USE_PREF_SIZE);
			radioButton.setMaxWidth(Region.USE_PREF_SIZE);
			radioButton.setMinHeight(Region.USE_PREF_SIZE);
			radioButton.setMaxHeight(Region.USE_PREF_SIZE);
			radioButton.selectedProperty().addListener((o, old, value) -> {
				if (value)
					setBarSpan(barSpan);
			});
		}
		Ui.box(this);
	}

	private void setBarSpan(BarSpan barSpan) {
		chartingView.barSpan(barSpan);
	}

	@Override
	public Node getNode() {
		return this;
	}

	private void linkChanged(Link old, Link link) {
		if (null != old) {
			old.remove(stateConsumer);
		}
		if (null != link) {
			link.add(stateConsumer);
			stateConsumer.accept(link.getState());
		}
	}

	private void linkStateChanged(State state) {
		if(null != state){
			for (Toggle toggle : toggleGroup.getToggles()) {
				final BarSpan barSpan = (BarSpan) toggle.getUserData();
				if (barSpan.equals(state.barSpan())) {
					toggle.setSelected(true);
					break;
				}
			}
		}
	}

	@Override
	public void attach(ChartingView chartingView) {
		this.chartingView = chartingView;
		final LinkButton linkButton = chartingView.getTitleBar().getLinkButton();
		linkButton.getLinkProperty().addListener((o, old, link) -> linkChanged(old, link));
		linkChanged(null, linkButton.getLink());
	}

}
