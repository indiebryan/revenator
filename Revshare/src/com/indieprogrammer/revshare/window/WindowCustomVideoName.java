package com.indieprogrammer.revshare.window;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WindowCustomVideoName {

	private static int titleLength;
	private static String videoTitle;

	public static int display(String[] videoTitleAsArray) {
		titleLength = 0;
		videoTitle = videoTitleAsArray[titleLength];

		//Create Window & Init Stage Settings
		Stage window = new Stage();
		window.setTitle("Select Video Name");
		window.initModality(Modality.APPLICATION_MODAL);

		//Create label to display video name
		Label labelVideoName = new Label(videoTitleAsArray[0]);
		labelVideoName.setWrapText(true);

		//The + button will extend the video title length by 1 CSV column
		Button addButton = new Button("+");
		addButton.setOnAction(e -> {
			titleLength++;
			videoTitle += videoTitleAsArray[titleLength];
			labelVideoName.setText(videoTitle);
		});

		//The - button will extend the video title length by 1 CSV column
		Button minusButton = new Button("-");
		minusButton.setOnAction(e -> {
			if (titleLength > 0) {
				titleLength--;
				String newTitle = "";
				for (int i = 0; i <= titleLength; i++)
					newTitle += videoTitleAsArray[i];
				videoTitle = newTitle;
				//videoTitle += videoTitleAsArray[titleLength];
				labelVideoName.setText(videoTitle);
			}
		});

		//The done button will close the window, returning the titleLength to wherever the window was created & displayed
		Button doneButton = new Button("Done");
		doneButton.setOnAction(e -> {
			window.close();
		});


		BorderPane layout = new BorderPane();
		Scene scene = new Scene(layout, 400, 250);

		HBox hB = new HBox();
		hB.getChildren().addAll(addButton, minusButton, doneButton);
		hB.setSpacing(10);

		layout.setTop(labelVideoName);
		layout.setCenter(hB);

		window.setScene(scene);
		window.showAndWait();

		return titleLength;
	}

}
