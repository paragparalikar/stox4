package com.stox.module.charting;

import java.util.Optional;
import java.util.function.Consumer;

import com.stox.workbench.link.Link;
import com.stox.workbench.link.Link.State;
import com.stox.workbench.link.LinkButton;
import com.stox.workbench.module.ModuleTitleBar;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Side;
import lombok.Getter;

public class ChartingTitleBar extends ModuleTitleBar {
	
	@Getter
	private final LinkButton linkButton = new LinkButton();
	private final Consumer<State> stateConsumer;
	
	public ChartingTitleBar(final Consumer<State> stateConsumer) {
		this.stateConsumer = stateConsumer;
		getTitleBar().append(Side.RIGHT, linkButton);
		linkButton.getLinkProperty().addListener(this::linkChanged);
	}

	private void linkChanged(final ObservableValue<? extends Link> observable, final Link old, final Link link) {
		Optional.ofNullable(old).ifPresent(o -> o.remove(stateConsumer));
		Optional.ofNullable(link).ifPresent(l -> {
			link.add(stateConsumer);
			stateConsumer.accept(link.getState());
		});
	}

}
