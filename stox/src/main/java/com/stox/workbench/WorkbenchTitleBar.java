package com.stox.workbench;

import com.stox.fx.fluent.scene.layout.FluentHBox;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.HasNode;
import com.stox.fx.widget.Spacer;
import com.stox.fx.widget.TitleBar;

import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Getter;

public class WorkbenchTitleBar implements HasNode<Node> {

	@Getter
	private final WorkbenchMenuBar menuBar;
	
	private final WindowControls windowControls;
	private final TitleBar titleBar = new TitleBar();
	
	public WorkbenchTitleBar(final FxMessageSource messageSource) {
		this.windowControls = new WindowControls(messageSource);
		this.menuBar = new WorkbenchMenuBar(messageSource);
		titleBar.center(new FluentHBox(menuBar.getNode(), new Spacer(), new AuthorContact().getNode())).right(windowControls.getNode());
		titleBar.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onMouseEvent);
	}

	private void onMouseEvent(MouseEvent event) {
		if (2 == event.getClickCount() && MouseButton.PRIMARY.equals(event.getButton())) {
			final Stage stage = Stage.class.cast(titleBar.getScene().getWindow());
			stage.setMaximized(!stage.isMaximized());
		}
	}

	@Override
	public Node getNode() {
		return titleBar;
	}

}
