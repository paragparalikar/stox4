package com.stox.module.charting;

import java.util.Optional;
import java.util.function.Consumer;

import com.stox.workbench.link.Link;
import com.stox.workbench.link.LinkButton;
import com.stox.workbench.link.LinkState;
import com.stox.workbench.module.ModuleTitleBar;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Side;
import lombok.Getter;

public class ChartingTitleBar extends ModuleTitleBar {
	
	@Getter
	private final LinkButton linkButton = new LinkButton();
	private final Consumer<LinkState> linkStateConsumer;
	
	public ChartingTitleBar(final Consumer<LinkState> linkStateConsumer) {
		this.linkStateConsumer = linkStateConsumer;
		getTitleBar().append(Side.RIGHT, linkButton);
		linkButton.getLinkProperty().addListener(this::linkChanged);
	}

	private void linkChanged(final ObservableValue<? extends Link> observable, final Link old, final Link link) {
		Optional.ofNullable(old).ifPresent(o -> o.remove(linkStateConsumer));
		Optional.ofNullable(link).ifPresent(l -> {
			link.add(linkStateConsumer);
			linkStateConsumer.accept(link.getState());
		});
	}

}
