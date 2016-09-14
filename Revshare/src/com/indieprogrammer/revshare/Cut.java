package com.indieprogrammer.revshare;

public class Cut {
	
	private float percentage;
	private String owner;
	
	public Cut(String owner, float percentage) {
		this.owner = owner;
		this.percentage = percentage;
	}

	public float getPercentage() {
		return percentage;
	}

	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	

}
