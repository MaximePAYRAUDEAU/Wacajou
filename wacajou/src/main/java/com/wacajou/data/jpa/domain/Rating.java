package com.wacajou.data.jpa.domain;
/**
 * 
 * @author Payraudeau Maxime
 *
 */
public enum Rating {
	HORRIBLE("Horrible", 0),
	TERRIBLE("Terrible", 1), 
	POOR("Poor", 2), 
	AVERAGE("Average", 3), 
	GOOD("Goode", 4), 
	EXCELLENT("Excellent", 5);
	
	private String name = "";
	private int value = 0;
	
	Rating(String name, int value){
		this.name = name;
		this.value = value;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getValue(){
		return this.value;
	}
}
