package com.wacajou.data.jpa.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Module extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "module")
	private Set<ParcoursModule> parcoursModule;

	@Column(name = "moduleName", unique = true)
	private String name;

	@OneToOne(fetch = FetchType.LAZY, targetEntity = User.class, optional = true)
	private User respo;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "module")
	private Set<UserModule> userModule;
	
	@Column(name = "description")
	private String description;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "module")
	private Set<Comments> comments;

	@Column(name = "domain")
	private String domain;
	
	@Column(name = "image")
	private String image;
	
	protected Module() {

	}

	public Module(String name, String description, String domain) {
		this.name = name;
		this.description = description;
		this.domain = domain;
	}

	public String getDescription() {
		return this.description;
	}
	
	public String getName(){
		return this.name;
	}
	
	public User getRespo() {
		return this.respo;
	}
	public Set<Comments> getComms(){
		return this.comments;
	}
	
	public String getDomain() {
		return this.domain;
	}
	
	public String getImage() {
		return this.image;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}