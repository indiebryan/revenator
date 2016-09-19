package com.indieprogrammer.revshare;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import com.indieprogrammer.revshare.io.CSVReader;
import com.indieprogrammer.revshare.window.WindowCreateCut;
import com.indieprogrammer.revshare.window.WindowCustomVideoName;
import com.indieprogrammer.revshare.window.WindowUserEditor;

public class Report {

	private CSVReader reader;
	private String name;
	private ArrayList<Video> videos;
	private ArrayList<User> users;
	private float totalRevenue;
	
	public Report(CSVReader reader) {
		this.reader = reader;
		this.videos = new ArrayList<Video>();
		this.name = reader.getReportName();
		this.users = WindowUserEditor.display("Users");
		this.totalRevenue = 0;
		
		createVideoList();
		createCutList();
	}
	
	//Read the CSV data passed with the reader into an ArrayList of videos
	private void createVideoList() {
		for (int i = 1; i < reader.getLines().size(); i++) {
			System.out.println(reader.readPoint(i, 0));
			//If the video title has a , in it the CSV will surround the title with quotes; handle for this situation, as well as handle for videos with multiple commas in the title
			
			//If the reader detects a title beginning with a quotation mark, this means there is a comma in the video title
			if (reader.readPoint(i, 0).substring(0, 1).equals("\"")) {
				
				//Get the videoTitleLength (number of commas in the video title) and store it
				int videoTitleLength = WindowCustomVideoName.display(reader.readLineAsArray(i));
				
				//Use videoTitleLength to read the full video title and locate the revenue in the CSV file
				videos.add(new Video(reader.readLineSnippet(i, 0, videoTitleLength), Float.parseFloat(reader.readPoint(i, 5 + videoTitleLength))));
			}
			else
				videos.add(new Video(reader.readPoint(i, 0), Float.parseFloat(reader.readPoint(i, 5))));
			
			//For each video added, add that video's revenue to the totalRevenue
			totalRevenue += videos.get(i - 1).getRevenue();
		}
		
	}
	
	//Create an ArrayList of Cuts to distinguish each User's revenue share for each video
	private void createCutList() {
		
		//Store the paths of the Cut and User working local directories
		File cutListDirectory = new File(System.getProperty("user.home") + "/AppData/Roaming/Revenator/CutLists");
		File userListDirectory = new File(System.getProperty("user.home") + "/AppData/Roaming/Revenator/Users");
		
		//Create working directory for cut lists in AppData
		cutListDirectory.mkdir();
		
		//Create working directory for user lists in AppData
		userListDirectory.mkdir();
		
		File cutList = new File(cutListDirectory.getAbsolutePath() + "/" + this.name);
		File userList = new File(userListDirectory.getAbsolutePath() + "/" + this.name);
		
		//Check if user list already exists, if not start a new one
		if (!userList.exists()) {
			try {
				//Create new local CSV file to store User list
				userList.createNewFile();

				FileWriter writer = new FileWriter(userList, false);

				writer.write("video");
				for (User u: users)
					writer.write("," + u.getName());

				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		//Check if cut list already exists, if not start a new one
		if (!cutList.exists()) {
			
			try {
			cutList.createNewFile();
			
			FileWriter writer = new FileWriter(cutList, false);
			
			writer.write("video");
			for (User u: users)
				writer.write("," + u.getName());
			
			//For each video, assign percentage shares amongst each User
			for (int i = 0; i < 4; i++) {
				
				//This window returns a String array representing the % share of each user in numerical order
				String[] cuts = WindowCreateCut.display(users, videos.get(i).getName());
				
				//Start on a new line and end with a comma for each video name
				writer.write("\n" + videos.get(i).getName() + ",");
				
				//For each User for each Video, add cuts based on String[] array
				for (int j = 0; j < users.size(); j++) {
					videos.get(i).addCut(users.get(j).getName(), Float.parseFloat(cuts[j]));
					//videos.get(i).addCut("Bryan", Float.parseFloat(cuts[0]));
					writer.write(cuts[j]);
					
					//Add a comma after each cut except for the last one in the line
					if (j != users.size() - 1)
						writer.write(",");
				}
			}
			
			writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			
			//If a cut list already exists, create a CSVReader and pass the file to it
			CSVReader reader = new CSVReader(cutList);
			
			//For each video
			for (int i = 0; i < 4; i++) {
				Video v = videos.get(i);
				int j = 1;
				
				//For each user for each video, add a cut representing the user's share to that video, and add that user's earned revenue to the user's total
				for (User u : users) {
					if (reader.findLineForString(v.getName()) == -666)
						System.out.println("Could not find " + v.getName() + " in the document.");
					v.addCut(u.getName(), Float.parseFloat(reader.readPoint(reader.findLineForString(v.getName()), j)));
					users.get(j - 1).addRevenue(Float.parseFloat(reader.readPoint(reader.findLineForString(v.getName()), j)) / 100 * v.getRevenue());
					j++;
				}
			}
		}
	}
	
	public ArrayList<Video> getVideos() {
		return videos;
	}
	
	public String getName() {
		return name;
	}
	
	public float getTotalRevenue() {
		return totalRevenue;
	}
	
	public ArrayList<User> getUsers() {
		return users;
	}
	
	public File getReportFile() {
		System.out.println("Returning file " + reader.getCSVFile().getAbsolutePath());
		return reader.getCSVFile();
	}
}
