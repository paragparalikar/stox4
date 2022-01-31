package com.stox.module.ranker.modal;

import com.stox.fx.fluent.scene.control.FluentComboBox;
import com.stox.fx.fluent.scene.control.FluentTextField;
import com.stox.fx.fluent.scene.layout.FluentBorderPane;
import com.stox.fx.fluent.scene.layout.FluentVBox;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Icon;
import com.stox.fx.widget.auto.AutoUiBuilder;
import com.stox.fx.widget.auto.AutoVBox;
import com.stox.module.ranker.RankerConfig;
import com.stox.module.ranker.model.Ranker;
import com.stox.workbench.modal.ActionModal;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RankerConfigEditorModal extends ActionModal<RankerConfigEditorModal> 
	implements ChangeListener<Ranker<?>>{
	
	@NonNull private final RankerConfig rankerConfig;
	@NonNull private final FxMessageSource messageSource;

	private Node caller;
	private Object config;
	private AutoVBox autoVBox;
	
	private final StackPane autoUiPane = new StackPane();
	private final FluentTextField offsetTextField = new FluentTextField("0")
			.promptText("Offset");
	private final FluentComboBox<Ranker<?>> rankerComboBox = new FluentComboBox<Ranker<?>>()
			.onSelect(this).fullWidth()
			.items(Ranker.ALL);
	private final FluentVBox content = new FluentVBox(offsetTextField, rankerComboBox, autoUiPane)
			.spacing(20);
	private final FluentBorderPane container = new FluentBorderPane(content).classes("padded", "content").fullArea();
	
	@Override
	public RankerConfigEditorModal show(Node caller) {
		this.caller = caller;
		graphic(Icon.ALIGN_RIGHT)
			.title(messageSource.get("Modify Ranker Configuration"))
			.content(container)
			.actionButtonText(messageSource.get("Modify"))
			.cancelButtonText(messageSource.get("Cancel"));
		rankerComboBox.select(null == rankerConfig.getRanker() ? 0 : rankerComboBox.getItems().indexOf(rankerConfig.getRanker()));
		return super.show(caller);
	}
	
	@Override
	public void changed(ObservableValue<? extends Ranker<?>> observable, Ranker<?> oldValue, Ranker<?> newValue) {
		config = newValue.defaultConfig();
		final AutoUiBuilder autoUiBuilder = new AutoUiBuilder();
		autoVBox = autoUiBuilder.build(config);
		autoVBox.populateView();
		autoUiPane.getChildren().setAll(autoVBox);
	}
	
	@Override
	protected void action() {
		autoVBox.populateModel();
		rankerConfig.setOffset(Integer.parseInt(offsetTextField.getText()));
		rankerConfig.setRanker(rankerComboBox.getValue());
		rankerConfig.setRankerConfig(config);
		caller.fireEvent(new RankerConfigChangedEvent());
		hide();
	}

	@Override
	protected RankerConfigEditorModal getThis() {
		return this;
	}

}
