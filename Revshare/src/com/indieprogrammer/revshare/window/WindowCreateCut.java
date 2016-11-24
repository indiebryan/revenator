package com.indieprogrammer.revshare.window;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.indieprogrammer.revshare.User;
import com.indieprogrammer.revshare.std.InputStandards;

public class WindowCreateCut {
	
	public static String[] display(ArrayList<User> users, String videoTitle) {
		String[] userCuts;
		
		VBox vb = new VBox();
		ArrayList<Label> nameLabels = new ArrayList<Label>();
		ArrayList<TextField> textFields = new ArrayList<TextField>();
		int i = 0;
		for (User u: users) {
			TextField field = new TextField();
			field.setMaxWidth(30);
			field = InputStandards.newTextField("Enter percentage");
			nameLabels.add(new Label(u.getName()));
			textFields.add(field);
			vb.getChildren().addAll(nameLabels.get(i), textFields.get(i));
			i++;
		}
		vb.setSpacing(10);
		
		Stage window = new Stage();
		window.setTitle(videoTitle);
		window.initModality(Modality.APPLICATION_MODAL);
		
		BorderPane layout = new BorderPane();
		Scene scene = new Scene(layout, 500, 150);
		
		layout.setCenter(vb);
		
		window.setScene(scene);
		window.showAndWait();
		
		userCuts = new String[users.size()];
		for (int j = 0; j < userCuts.length; j++) {
			if (!textFields.get(j).getText().isEmpty())
				userCuts[j] = textFields.get(j).getText();
			else
				userCuts[j] = "0";
		}
		
		return userCuts;
	}

}
