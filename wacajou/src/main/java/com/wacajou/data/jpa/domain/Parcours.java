package com.wacajou.data.jpa.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Parcours extends AbstractEntity{
	private static final long serialVersionUID = 1L;
	

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parcours")
	private Set<ParcoursModule> parcoursModule;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parcours")
	private Set<UserParcours> userParcours;
	
	@OneToOne(fetch = FetchType.LAZY, targetEntity = User.class, optional = true)
	private User respo;
	
	private String name;
	

	@Column(name = "description")
	private String description;

	@Column(name = "image")
	private String image;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parcours")
	private Set<Comments> comments;

	protected Parcours(){
		
	}
	
	public Parcours(String name, String description){
		this.name = name;
		this.description = description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	public String getName(){
		return this.name;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public User getRespo(){
		return this.respo;
	}
	
	public Set<Comments> getComms(){
		return this.comments;
	}
	
	public String getImage() {
		return this.image;
	}
}