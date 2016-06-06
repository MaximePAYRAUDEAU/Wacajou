package com.wacajou.data.jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
public class UserInfo extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToOne(fetch = FetchType.LAZY, targetEntity = User.class, optional = false)
	private User user;
	
	@Column(name = "image", nullable = true)
	private String image;

	@Column(name = "LDM", nullable = true)
	private String ldm;

	@Column(name = "CV", nullable = true)
	private String cv;

	@Column(name = "mark", nullable = true)
	private String mark;

	@Column(name = "intership", nullable = true)
	private String intership;

	@Column(name = "university", nullable = true)
	private String university;

	@Column(name = "activities", nullable = true)
	private String activities;

	public void setLdm(String ldm) {
		this.ldm = ldm;
	}

	public void setCv(String cv) {
		this.cv = cv;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public void setIntership(String intership) {
		this.intership = intership;
	}
	
	public void setUniversity(String university){
		this.university = university;
	}
	
	public void setActivities(String activities){
		this.activities = activities;
	}
	
	public void setImage(String image){
		this.image = image;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public String getLdm(){
		return this.ldm;
	}
	
	public String getCv(){
		return this.cv;
	}
	
	public String getMark(){
		return this.mark;
	}
	
	public String getIntership(){
		return this.intership;
	}
	
	public String getUniversity(){
		return this.university;
	}
	
	public String getActivities(){
		return this.activities;
	}
	
	public String getImage(){
		return this.image;
	}

	public User getUser(){
		return this.user;
	}
	
	
}