package com.indieprogrammer.revshare;

import java.util.ArrayList;

public class Video {
	
	private String name;
	private float revenue;
	private ArrayList<Cut> cuts;
	
	public Video(String name, float revenue) {
		this.name = trimName(name);
		this.revenue = revenue;
		this.cuts = new ArrayList<Cut>();
	}
	
	
	//Trim name and remove special starting characters
	private String trimName(String name) {
		String newName = name.trim();
		if (newName.substring(0, 1).trim().equals("â") || newName.trim().substring(0, 1).equals("?") || newName.trim().substring(0, 1).equals("►")) {
			System.out.println("FOUND TRIANGLE");
			newName = newName.substring(2, newName.length());
		}
		return newName;
	}
	
	public void setRevenue(float revenue) {
		this.revenue = revenue;
	}
	
	public void addCut(Cut cut) {
		cuts.add(cut);
	}
	
	public void addCut(String owner, float percentage) {
		System.out.println("Adding cut for " + owner + " of " + percentage);
		cuts.add(new Cut(owner, percentage));
	}
	
	public String getName() {
		return name;
	}
	
	public float getRevenue() {
		return revenue;
	}
	
	public ArrayList<Cut> getCuts() {
		return cuts;
	}

}
