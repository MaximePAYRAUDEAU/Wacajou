package com.wacajou.data.jpa.domain;
/**
 * 
 * @author Payraudeau Maxime
 *
 */
public enum Statut {
	STUDENT(0, "Eleve", true), 
	ANCIEN(1, "Ancien", false), 
	RESPO_MODULE(2, "Responsable de module", true), 
	RESPO_PARCOURS(3, "Responsable de parcours", true), 
	RESPO_PEDAGOGIQUE(4, "Responsable pédagogique", true), 
	ADMIN(5, "Administrateur", false);
	
	private int value = 0;
	private String message;
	private boolean canCreate;
	
	Statut(int value, String message, boolean canCreate){
		this.value = value;
		this.message = message;
		this.canCreate = canCreate;
	}
	
	public int getValue(){
		return this.value;
	}

	public String getMessage(){
		return this.message;
	}
	
	public boolean canCreate(){
		return this.canCreate;
	}
}