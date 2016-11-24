package com.indieprogrammer.revshare.window;

import java.util.ArrayList;

import com.indieprogrammer.revshare.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WindowUserEditor {
	
	//Creates a window allowing the user to add/remove users from a selected channel
	
	//Returns an ArrayList of User objects upon instantiation
	public static ArrayList<User> display(String videoTitle) {
		ArrayList<User> users = new ArrayList<User>();
		
		Stage window = new Stage();
		window.setTitle(videoTitle);
		window.initModality(Modality.APPLICATION_MODAL);
		
		ListView<String> list = new ListView<String>();
		ObservableList<String> userNames = FXCollections.observableArrayList();
		list.setPrefHeight(24 * 6);
		list.setPrefWidth(260);
		list.setItems(userNames);
		
		Label nameLabel = new Label("Name:");
		TextField nameField = new TextField();
		
		Button addButton = new Button("+");
		addButton.setOnAction(e -> {
			if (!nameField.getText().trim().isEmpty()) {
				userNames.add(nameField.getText().trim());
				users.add(new User(nameField.getText().trim()));
				nameField.clear();
			}
		});
		
		Button removeButton = new Button("-");
		removeButton.setOnAction(e -> {
			Object selected = list.getSelectionModel().getSelectedItem();
			users.remove(selected);
			userNames.remove(selected);
		});
		
		Button doneButton = new Button("Done");
		doneButton.setOnAction(e -> {
			window.close();
		});
		
		HBox hbt = new HBox();
		hbt.getChildren().addAll(list);
		hbt.setSpacing(10);
		
		HBox hbb = new HBox();
		hbb.getChildren().addAll(nameLabel, nameField, addButton, removeButton);
		hbb.setSpacing(10);
		
		BorderPane layout = new BorderPane();
		Scene scene = new Scene(layout, 400, 250);
		
		layout.setTop(hbt);
		layout.setCenter(hbb);
		layout.setBottom(doneButton);
		
		window.setScene(scene);
		window.showAndWait();
		
		//userOptionValues[1] = liPercent.getText();
		
		return users;
	}

}
