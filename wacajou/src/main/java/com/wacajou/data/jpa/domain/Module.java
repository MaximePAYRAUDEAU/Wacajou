package com.wacajou.data.jpa.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
/**
 * 
 * @author Payraudeau Maxime
 *
 */
@Entity
public class Module extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "module")
	private Set<ParcoursModule> parcoursModule;

	@Column(name = "moduleName", unique = true, nullable = false)
	private String name;

	@OneToOne(fetch = FetchType.LAZY, targetEntity = User.class, optional = true)
	private User respo;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "module")
	private Set<UserModule> userModule;

	@Column(name = "description", nullable = true)
	private String description;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "module")
	private Set<Comments> comments;

	@Column(name = "domain", nullable = false)
	private Domain domain;

	@Column(name = "image", nullable = true)
	private String image;

	public Module() {

	}

	public void Create(String name, String description, String image, Domain domain) {
		this.name = name;
		this.description = description;
		this.domain = domain;
		this.image = image;
	}

	public User getRespo() {
		return this.respo;
	}
	
	public void setRespo(User user) {
		this.respo = user;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name){
		this.name = name;
	}
	
	public Set<Comments> getComms() {
		return this.comments;
	}

	public Domain getDomain() {
		return this.domain;
	}

	public void setDomain(Domain domain){
		this.domain = domain;
	}
	
	public String getImage() {
		return this.image;
	}

}