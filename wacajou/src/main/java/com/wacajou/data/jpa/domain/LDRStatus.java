package com.wacajou.data.jpa.domain;
/**
 * 
 * @author Payraudeau Maxime
 *
 */
public enum LDRStatus {
	SEND(0, "Envoyé"), 
	RECIVE(1, "Reçu"), 
	IN_PROGRESS(2, "En attente"),
	COMPLETE(3, "Compléter");
	
	private int value;
	private String message;
	
	LDRStatus(int value, String message){
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
