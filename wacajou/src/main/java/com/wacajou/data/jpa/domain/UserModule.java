package com.wacajou.data.jpa.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
public class UserModule extends AbstractEntity{

	private static final long serialVersionUID = 1L;
	
	@OneToOne(fetch = FetchType.LAZY, targetEntity = User.class)
	private User user;

	@OneToOne(fetch = FetchType.LAZY, targetEntity = Module.class)
	private Module module;
	
	protected UserModule(){
		
	}
	
	public UserModule(User user, Module module){
		this.user = user;
		this.module = module;
	}
	
	public User getUser(){
		return this.user;
	}
	
	public Module getModule(){
		return this.module;
	}

}