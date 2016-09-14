package com.indieprogrammer.revshare;
import com.indieprogrammer.revshare.window.WindowMain;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Revenator :: IndieProgrammer.com/revenator");
		new WindowMain(primaryStage);
		
	}

}
