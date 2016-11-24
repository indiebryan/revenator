package com.indieprogrammer.revshare;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;

public class Channel {

	private String name;
	private ArrayList<Report> reports;
	private File channelFile;

	public Channel(String name) {
		this.name = name;
		this.reports = new ArrayList<Report>();
	}

	public Channel() {
		this.name = "NAME_NOT_SET";
		this.reports = new ArrayList<Report>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Report> getReports() {
		return reports;
	}

	public void setReports(ArrayList<Report> reports) {
		this.reports = reports;
	}

	/*
	 * BUG: For some reason, the Files.copy line is returning a NoSuchFileException
	 */
	public void addReport(Report report) {
		this.reports.add(report);
		try {
			File createChannelDirectory = new File(channelFile.toString());
			createChannelDirectory.mkdir();
			Files.copy(report.getReportFile().toPath(), (new File(channelFile.toString() + "/" + report.getName()).toPath()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//saveChannelToFile();
		System.out.println("Channel " + name + " Added Report " + report.getName());
	}
	
	public void saveChannelToFile() {
		if (this.channelFile != null && this.channelFile.exists()) {
			try {
			FileWriter writer = new FileWriter(channelFile, false);
			PrintWriter pw = new PrintWriter(writer, false);
			pw.flush();
			writer.write(name);
			for (Report r : reports)
				writer.write("\n" + r.getName());
			pw.close();
			writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public File getChannelFile() {
		return channelFile;
	}

	public void setChannelFile(File channelFile) {
		this.channelFile = channelFile;
	}
}
