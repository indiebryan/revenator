package com.indieprogrammer.revshare;

public class User {
	
	private String name;
	private float revenue;
	
	public User(String name) {
		this.name = name;
		this.revenue = 0;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setRevenue(float revenue) {
		this.revenue = revenue;
	}
	
	public void addRevenue(float revenue) {
		this.revenue += revenue;
	}
	
	public String getName() {
		return this.name;
	}
	
	public float getRevenue() {
		return this.revenue;
	}

}
