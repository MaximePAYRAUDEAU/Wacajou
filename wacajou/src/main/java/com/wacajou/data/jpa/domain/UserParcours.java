package com.wacajou.data.jpa.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
public class UserParcours extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	
	@OneToOne(fetch = FetchType.LAZY, targetEntity = User.class)
	private User user;

	@OneToOne(fetch = FetchType.LAZY, targetEntity = Parcours.class)
	private Parcours parcours;

	protected UserParcours(){
		
	}
	
	public UserParcours(User user, Parcours parcours){
		this.user = user;
		this.parcours = parcours;
	}
	
	public User getUser(){
		return this.user;
	}
	
	public Parcours getParcours(){
		return this.parcours;
	}

}