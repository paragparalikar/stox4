package com.stox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Icon;
import com.stox.workbench.Workbench;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

@SpringBootApplication
public class StoxApplication extends Application{

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void init() throws Exception {
		super.init();
		Font.loadFont(Icon.class.getClassLoader().getResource(Icon.PATH).toExternalForm(), 10);
	}
	
	@Override
	public void start(Stage ignored) throws Exception {
		SpringApplication.run(StoxApplication.class);
	}
	
	@Bean
	public FxMessageSource fxMessageSource() {
		return new FxMessageSource();
	}
	
	@Bean(initMethod = "show", destroyMethod = "hide")
	public Workbench workbench(final FxMessageSource messageSource) {
		return new Workbench(messageSource);
	}
}
