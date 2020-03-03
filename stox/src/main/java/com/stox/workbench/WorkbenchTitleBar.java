package com.stox.workbench;

import java.util.Collections;
import java.util.Optional;

import com.stox.fx.fluent.scene.layout.FluentHBox;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.HasNode;
import com.stox.fx.widget.Spacer;
import com.stox.fx.widget.TitleBar;
import com.stox.workbench.link.Link;

import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import lombok.Getter;
import lombok.NonNull;

public class WorkbenchTitleBar implements HasNode<Node> {

	@Getter
	private final WorkbenchMenuBar menuBar;
	private final WindowControls windowControls;
	private final TitleBar titleBar = new TitleBar();

	public WorkbenchTitleBar(final FxMessageSource messageSource) {
		this.windowControls = new WindowControls(messageSource);
		this.menuBar = new WorkbenchMenuBar(messageSource);
		titleBar.center(new FluentHBox(menuBar.getNode(), new Spacer(), new AuthorContact().getNode())).append(Side.RIGHT, windowControls.getNode());
		titleBar.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, this::onMouseEvent);
	}
	
	WorkbenchTitleBar state(@NonNull final WorkbenchState state) {
		windowControls.state(state);
		Optional.ofNullable(state.linkStates()).orElse(Collections.emptyMap()).forEach((color, linkState) -> {
			Optional.ofNullable(linkState).ifPresent(value -> Optional.ofNullable(Link.link(color)).ifPresent(link -> link.setState(value)));
		});
		return this;
	}
	
	private void onMouseEvent(MouseEvent event) {
		if (2 == event.getClickCount() && MouseButton.PRIMARY.equals(event.getButton())) {
			windowControls.toggleMaximizeRestore(null);
		}
	}
	
	boolean maximized() {
		return windowControls.maximized();
	}

	@Override
	public Node getNode() {
		return titleBar.getNode();
	}

}
