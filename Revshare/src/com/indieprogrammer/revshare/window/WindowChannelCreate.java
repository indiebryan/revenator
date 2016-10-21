package com.indieprogrammer.revshare.window;

import java.io.File;
import java.io.IOException;

import com.indieprogrammer.revshare.Channel;

import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WindowChannelCreate {

	public static Channel display() {
		//Window init
		Stage window = new Stage();
		window.setTitle("Create Channel");
		window.initModality(Modality.APPLICATION_MODAL);

		BorderPane layout = new BorderPane();
		Scene scene = new Scene(layout, 250, 90);
		//scene.getStylesheets().add("revstyle.css");
		
		//Variable to be returned
		Channel channel = new Channel();

		//Button HBox holds Continue / Cancel buttons
		HBox hbButtons = new HBox(5);

		//Buttons
		Button buttonCancel = new Button("Cancel");
		Button buttonFinish = new Button("Finish");

		//Name HBox holds label and textfield
		HBox hbName = new HBox(5);

		//Channel name label
		Label labelName = new Label("Name:");

		//Channel name textfield
		TextField fieldName = new TextField();
		fieldName.setOnKeyPressed(e -> {

		});
		
		//Make folder directory for channels
		new File(System.getProperty("user.home") + "/AppData/Roaming/Revenator/Channels").mkdir();

		//This boolean binding ensures a new channel 
		//cannot be created with the same name as one that already exists
		BooleanBinding bb = new BooleanBinding() {
			{
				super.bind(fieldName.textProperty());
			}

			@Override
			protected boolean computeValue() {
				return (new File(System.getProperty("user.home") + "/AppData/Roaming/Revenator/Channels/" + 
						fieldName.getText().trim()).exists());
			}
		};

		buttonFinish.disableProperty().bind(bb);

		//Add elements to HBox Name
		hbName.getChildren().addAll(labelName, fieldName);

		//Add elements to HBox Buttons
		hbButtons.getChildren().addAll(buttonCancel, buttonFinish);

		//Set buttonFinish action after init fieldName
		buttonFinish.setOnAction(e -> {
			channel.setName((fieldName.getText().trim()));
			try {
				File channelFile = new File(System.getProperty("user.home") + "/AppData/Roaming/Revenator/Channels/" + fieldName.getText().trim());
				channel.setChannelFile(channelFile);
				channelFile.mkdir();
				//FileWriter writer = new FileWriter(channelFile, false);
				//writer.write(channel.getName());
				//writer.close();
				//channel.setChannelFile(channelFile);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			window.close();
		});

		//Add HBoxes to layout
		VBox vb = new VBox();
		hbName.setAlignment(Pos.CENTER);
		hbButtons.setAlignment(Pos.CENTER);
		hbButtons.setPadding(new Insets(10, 10, 10, 10));
		vb.getChildren().addAll(hbName, hbButtons);
		vb.setAlignment(Pos.CENTER);
		layout.setCenter(vb);
		window.setScene(scene);
		window.showAndWait();

		return channel;
	}

}
