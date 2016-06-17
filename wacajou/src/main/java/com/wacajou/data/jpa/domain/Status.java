package com.wacajou.data.jpa.domain;
/**
 * 
 * @author Payraudeau Maxime
 *
 */
public enum Status {
	CREATED(0, "Crée"),
	VALIDATE(1, "Validé"),
	CERTIFIED(2, "Certifié"),
	CURRENT(3, "Actuel"),
	OUTDATED(4, "Non actuel"),
	TO_BE_DELETED(5, "A supprimer"),
	DELETED(6, "Supprimé");
	
	private int value;
	private String message;
	
	Status(int value, String message){
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
