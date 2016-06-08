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
public class Parcours extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parcours")
	private Set<ParcoursModule> parcoursModule;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parcours")
	private Set<UserParcours> userParcours;

	@OneToOne(fetch = FetchType.LAZY, targetEntity = User.class, optional = true )
	private User respo;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description", nullable = true)
	private String description;

	@Column(name = "image", nullable = true)
	private String image;

	@Column(name = "domain", nullable = false)
	private Domain domain;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parcours")
	private Set<Comments> comments;

	public Parcours() {

	}

	public void Create(String name, String description, String image, Domain domain) {
		this.name = name;
		this.description = description;
		this.image = image;
		this.domain = domain;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setRespo(User user) {
		this.respo = user;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public User getRespo() {
		return this.respo;
	}

	public Set<Comments> getComms() {
		return this.comments;
	}

	public String getImage() {
		return this.image;
	}

	public Domain getDomain() {
		return this.domain;
	}

	@Override
	public String toString() {
		return "Id=" + getId() + ", Name=" + getName() + ", Description=" + getDescription() + ", Image=" + getImage()
				+ ", Domain=" + getDomain().toString();
	}

}