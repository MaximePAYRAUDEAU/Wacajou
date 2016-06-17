package com.wacajou.data.jpa.domain;

public enum Domain {
	INFORMATIC(0, "Informatique"), 
	ELECTRONIC(1, "Electronique"),
	LANGUAGE(2, "Langues"), 
	MANAGERIAL(3, "Managériale"), 
	TELECOMS(4, "Télécoms"),
	IMAGERIE(5, "Imagerie"),
	OTHER(100, "Other");
	
	private int value;
	private String message;
	
	Domain(int value, String message){
		this.value = value;
		this.message = message;
	}
	
	public int getValue(){
		return this.value;
	}
	
	public String getMessage(){
		return this.message;
	}
}
