package com.indieprogrammer.revshare.window;

import java.io.File;

import com.indieprogrammer.revshare.BarGraph;
import com.indieprogrammer.revshare.Channel;
import com.indieprogrammer.revshare.Report;
import com.indieprogrammer.revshare.User;
import com.indieprogrammer.revshare.io.CSVReader;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class WindowMain {

	//Window stage & scene variables
	private BorderPane layout;
	private Scene scene;
	private Stage window;
	
	/*
	 * Menu Variables
	 */
	
	private MenuBar menuBar;
	
	//File Menu
	Menu menuFile, menuFileNew;
	MenuItem[] menuFileItems, menuFileNewItems;
	
	//Channel Menu
	Menu menuChannel, menuChannelAdd;
	MenuItem[] menuChannelItems, menuChannelAddItems;

	//Data variables
	private Channel channel;
	//private Report report;

	public WindowMain(Stage window) {

		//Set window & scene properties
		this.window = window;
		this.layout = new BorderPane();
		this.scene = new Scene(layout, 500, 500);
		//this.scene.getStylesheets().add(getClass().getClassLoader().getResource("revstyle.css").toString());

		//Setup top menu bar
		setupMenus();
		this.layout.setTop(menuBar);
		window.setScene(scene);
		window.show();
	}

	//Load a CSV file and display report on the window
	private Report loadReport() {

		//Create a nuser FileChooser for the user to locate the CSV file
		FileChooser fc = new FileChooser();

		//Set custom options for FileChooser such as file type filtering
		configureFileChooser(fc);
		fc.setTitle("Import CSV");

		//Create a CSVReader with the chosen file
		CSVReader csvr = new CSVReader(fc.showOpenDialog(window));

		//Create a new report and pass the CSVReader as an argument
		Report report = new Report(csvr, this.channel);

		//Create a new bar graph and pass the Report as an argument
		BarGraph bar = new BarGraph(report);

		//Create a new vertical box holder to display revenue data on the right side of the window
		VBox vb = new VBox();

		//Display total earned revenue amongst all videos and add it to the VBox
		Label lblRevenue = new Label("Total Revenue: $" + String.valueOf(report.getTotalRevenue()));
		vb.getChildren().add(lblRevenue);

		//For each User in the report, create a separate label with their own revenue earnings and add it to the VBox
		for (User u : report.getUsers()) {
			Label userLabel = new Label(u.getName() + ":  $" + u.getRevenue());
			vb.getChildren().add(userLabel);
		}

		//Set VBox spacing and add it to the layout
		vb.setSpacing(10);
		this.layout.setRight(vb);

		//Set the bar graph at the center of the layout
		this.layout.setCenter(bar.getGraph());
		return report;
	}

	//Open Channel or Report file
	private void openFile() {
		DirectoryChooser dc = new DirectoryChooser();
		dc.setInitialDirectory(new File(System.getProperty("user.home") + "/AppData/Roaming/Revenator"));
		
		File chosenFile = dc.showDialog(window);
		window.setTitle(chosenFile.toString() + " - Revenator");
		File[] channelReports = chosenFile.listFiles();
		for (File f : channelReports)
			System.out.println("REPORTS: " + f.getAbsolutePath());
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
		//Create menuBar to hold all menu options
		menuBar = new MenuBar();

		setupFileMenu(menuBar);
		setupChannelMenu(menuBar);

		//Set "Channel" menu disabled by default, since no channel is active
		//menuBar.getMenus().get(1).setDisable(true);
	}

	private void setupFileMenu(MenuBar menuBar) {
		//Create fileMenu to hold "File" options
		menuFile = new Menu("File");

		//Create newMenu submenu (File -> New)
		menuFileNew = new Menu("New");

		//Create newMenu items & their actions
		menuFileNewItems = new MenuItem[2];
		menuFileNewItems[0] = new MenuItem("Channel");
		menuFileNewItems[0].setOnAction(e -> {
			this.channel = WindowChannelCreate.display();
			this.window.setTitle(this.channel.getName() + " - Revenator");
			this.menuChannelAdd.setDisable(false);
		});
		menuFileNewItems[1] = new MenuItem("Report");
		menuFileNewItems[1].setOnAction(e -> loadReport());

		//Add newMenu items to newMenu
		for (MenuItem mI : menuFileNewItems)
			menuFileNew.getItems().add(mI);

		//Create fileMenu items & their actions
		menuFileItems = new MenuItem[3];

		menuFileItems[0] = new MenuItem("Open...");
		menuFileItems[0].setOnAction(e -> openFile());

		menuFileItems[1] = new MenuItem("Save");
		//fileMenuItems[1].setOnAction(e -> new WindowSave());

		menuFileItems[2] = new MenuItem("Exit");
		menuFileItems[2].setOnAction(e -> System.exit(0));

		//Add fileMenu items to fileMenu
		menuFile.getItems().add(menuFileNew);
		for (MenuItem mI : menuFileItems)
			menuFile.getItems().add(mI);

		//Hold menu buttons in menuBar
		menuBar.getMenus().addAll(menuFile);
	}

	private void setupChannelMenu(MenuBar menuBar) {

		//Create fileMenu to hold "File" options
		menuChannel = new Menu("Channel");

		//Create addMenu submenu (Channel -> Add)
		menuChannelAdd = new Menu("Add");

		//Create addMenu items & their actions
		menuChannelAddItems = new MenuItem[2];
		menuChannelAddItems[0] = new MenuItem("Report..");
		menuChannelAddItems[0].setOnAction(e -> {
			this.channel.addReport(loadReport());
			//this.channel = WindowChannelCreate.display();
			//window.setTitle(this.channel.getName() + " - Revenator");
		});
		menuChannelAddItems[1] = new MenuItem("User..");
		menuChannelAddItems[1].setOnAction(e -> loadReport());

		//Add addMenu items to addMenu
		for (MenuItem mI : menuChannelAddItems)
			menuChannelAdd.getItems().add(mI);

		//Other ChannelMenu options
		menuChannelItems = new MenuItem[1];
		menuChannelItems[0] = new MenuItem("Properties");

		//Add options to ChannelMenu
		menuChannel.getItems().add(menuChannelAdd);
		menuChannel.getItems().add(menuChannelItems[0]);

		//Disable Menu options since no Channel is currently active
		menuChannelAdd.setDisable(true);
		menuChannelItems[0].setDisable(true);

		//Hold menu buttons in menuBar
		menuBar.getMenus().addAll(menuChannel);

	}

}
