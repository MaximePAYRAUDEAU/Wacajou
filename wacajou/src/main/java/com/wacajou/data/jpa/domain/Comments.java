package com.wacajou.data.jpa.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
/**
 * 
 * @author Payraudeau Maxime
 *
 */
@Entity
public class Comments extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@OneToOne(optional = true, fetch = FetchType.LAZY, targetEntity = Module.class)
	private Module module;

	@OneToOne(optional = true, fetch = FetchType.LAZY, targetEntity = Parcours.class)
	private Parcours parcours;
	
	@OneToOne(fetch = FetchType.LAZY, targetEntity = User.class)
	private User user;
	
	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private Rating rating;

	@Column(nullable = false, length = 30)
	private String title;

	@Column(nullable = true, length = 5000)
	private String details;
	
	@Column(name = "logdate")
	private Date logdate;
	
	protected Comments() {
	}

	public Comments(Module module, User user, String title, Rating rating, String message) {
		this.module = module;
		this.rating = rating;
		this.title = title;
		this.details = message;
		this.user = user;
	}
	
	public Comments(Parcours parcours, User user, String title, Rating rating, String message) {
		this.parcours = parcours;
		this.rating = rating;
		this.title = title;
		this.details = message;
		this.user = user;
	}

	public User getUser() {
		return this.user;
	}
	
	public Module getModule() {
		return this.module;
	}

	public Parcours getParcours() {
		return this.parcours;
	}

	public Rating getRating() {
		return this.rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetails() {
		return this.details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	

	public Date getLogdate(){
		return this.logdate;
	}
	
	@PrePersist
	@PreUpdate
	public void setLogDate(){
		this.logdate = new Date();
	}
}