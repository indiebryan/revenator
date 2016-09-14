package com.indieprogrammer.revshare.window;

import java.io.File;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import com.indieprogrammer.revshare.BarGraph;
import com.indieprogrammer.revshare.Report;
import com.indieprogrammer.revshare.User;
import com.indieprogrammer.revshare.io.CSVReader;


public class WindowMain {

	private BorderPane layout;
	private MenuBar menuBar;
	private Scene scene;
	private Stage window;

	public WindowMain(Stage window) {
		
		//Set window & scene properties
		this.window = window;
		this.layout = new BorderPane();
		this.scene = new Scene(layout, 500, 500);

		//Setup top menu bar
		setupMenus();

		this.layout.setTop(menuBar);
		window.setScene(scene);
		window.show();
		
	}

	//Load a CSV file and display report on the window
	private void loadReport() {
		
		//Create a nuser FileChooser for the user to locate the CSV file
		FileChooser fc = new FileChooser();
		
		//Set custom options for FileChooser such as file type filtering
		configureFileChooser(fc);
		fc.setTitle("Import CSV");
		
		//Create a CSVReader with the chosen file
		CSVReader csvr = new CSVReader(fc.showOpenDialog(window));
		
		//Create a new report and pass the CSVReader as an argument
		Report r = new Report(csvr);
		
		//Create a new bar graph and pass the Report as an argument
		BarGraph bar = new BarGraph(r);
		
		//Create a new vertical box holder to display revenue data on the right side of the window
		VBox vb = new VBox();
		
		//Display total earned revenue amongst all videos and add it to the VBox
		Label lblRevenue = new Label("Total Revenue: $" + String.valueOf(r.getTotalRevenue()));
		vb.getChildren().add(lblRevenue);
		
		//For each User in the report, create a separate label with their own revenue earnings and add it to the VBox
		for (User u : r.getUsers()) {
			Label userLabel = new Label(u.getName() + ":  $" + u.getRevenue());
			vb.getChildren().add(userLabel);
		}
		
		//Set VBox spacing and add it to the layout
		vb.setSpacing(10);
		this.layout.setRight(vb);
		
		//Set the bar graph at the center of the layout
		this.layout.setCenter(bar.getGraph());
		
	}
	
	//Set custom options for FileChooser such as file type filtering
	private void configureFileChooser(FileChooser fileChooser) {
		fileChooser.setInitialDirectory(
	            new File(System.getProperty("user.home") + "/Desktop")
		        ); 
		
		fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV File", "*.csv"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
            );
	}

	//Create top-menu options
	private void setupMenus() {
		//Create fileMenu and menuBar
		Menu fileMenu = new Menu("File");
		menuBar = new MenuBar();

		//Create newMenu
		Menu newMenu = new Menu("New");

		//Create newMenu items & their actions
		MenuItem[] newMenuItems = new MenuItem[2];
		newMenuItems[0] = new MenuItem("Channel");
		newMenuItems[0].setOnAction(e -> {
			//WindowNewWorld.display();
			//world.reset(graphicsContext);
		});
		newMenuItems[1] = new MenuItem("Report");
		newMenuItems[1].setOnAction(e -> loadReport());

		//Add newMenu items to newMenu
		for (MenuItem mI : newMenuItems)
			newMenu.getItems().add(mI);

		//Create fileMenu items & their actions
		MenuItem[] fileMenuItems = new MenuItem[3];

		fileMenuItems[0] = new MenuItem("Open...");
		//fileMenuItems[0].setOnAction(e -> new WindowOpen());

		fileMenuItems[1] = new MenuItem("Save");
		//fileMenuItems[1].setOnAction(e -> new WindowSave());

		fileMenuItems[2] = new MenuItem("Exit");
		fileMenuItems[2].setOnAction(e -> System.exit(0));

		//Add fileMenu items to fileMenu
		fileMenu.getItems().add(newMenu);
		for (MenuItem mI : fileMenuItems)
			fileMenu.getItems().add(mI);

		//Hold menu buttons in menuBar
		menuBar.getMenus().addAll(fileMenu);
	}

}
