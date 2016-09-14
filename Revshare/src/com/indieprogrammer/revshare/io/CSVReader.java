package com.indieprogrammer.revshare.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class CSVReader {
	
	List<String> lines;
	int lineNumber = 0;
	String[][] content;
	String reportName;
	
	//Constructor takes a File as an argument passed by a FileChooser
	public CSVReader(File csv) {
		try {
			File revenatorWorkingDirectory = new File(System.getProperty("user.home") + "/AppData/Roaming/Revenator");
			
			//Make AppData/Roaming/Revenator directory to store csv files locally
			revenatorWorkingDirectory.mkdir();
			
			//When importing a CSV file, check if it has already been copied to directory.  If not, copy it there.
			File checkIfExists = new File(System.getProperty("user.home") + "/AppData/Roaming/Revenator/" + csv.getName());
			if (!checkIfExists.exists()) {
				Files.copy(csv.toPath(), (new File(System.getProperty("user.home") + "/AppData/Roaming/Revenator/" + csv.getName()).toPath()));
				
				//File doesn't exist, call PARSEREPORTNAME()
				this.reportName = parseReportName(csv.getName());
			}
			
			//File exists, set report name equal to file name
			this.reportName = csv.getName();
			
			//Set LINES equal to a list of each line of text in the csv
			this.lines = Files.readAllLines(csv.toPath());
			
			//Initialize CONTENT array by providing size data of LINES list
			content = new String[lines.size()][lines.get(0).split(",").length];
			
			//Fill CONTENT array
			//Any data from the CSV can now be called in grid notation, beginning with [0][0] for the top-left word in the file
			for (int i = 0; i < content.length; i++) {
				content[i] = lines.get(i).split(",");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Parse a readable report name from the original file name
	private String parseReportName(String fileName) {
		
		String startDate = fileName.substring(fileName.length() - 21, fileName.length() - 13);
		String endDate = fileName.substring(fileName.length() - 12, fileName.length() - 4);
		
		return startDate + " - " + endDate;
	}
	
	//Return the content of a content item as a String using grid notation
	public String readPoint(int lineNumber, int columnNumber) {
		return content[lineNumber][columnNumber];
	}
	
	//Return the content of an entire row of a CSV file as a String
	public String readLine(int lineNumber) {
		String line = "READLINE: ";
		for (int i = 0; i < content[lineNumber].length; i++)
			line += content[lineNumber][i];
		return line;
	}
	
	//Return row of CSV file as a String array
	public String[] readLineAsArray(int lineNumber) {
		return content[lineNumber];
	}
	
	//Return a horizontal sub-section of the CSV file as a String
	public String readLineSnippet(int lineNumber, int columnStart, int columnStop) {
		String snippet = "";
		for (int i = columnStart; i < columnStop; i++)
			snippet += content[lineNumber][i];
		return snippet;
	}
	
	public String getReportName() {
		return reportName;
	}
	
	public List<String> getLines() {
		return lines;
	}
	
	//Search the CSV file for a string and return the line number it first appears on
	//Remember the top line of the CSV file is reserved for tags & user ID's
	public int findLineForString(String query) {
		for (int i = 1; i < lines.size(); i++) {
			if (readPoint(i, 0).trim().equals(query.trim())) {
				return i;
			}
		}
		return -666;
	}
	
}
