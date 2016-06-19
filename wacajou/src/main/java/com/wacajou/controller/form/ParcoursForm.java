package com.wacajou.controller.form;

import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.wacajou.data.jpa.domain.Domain;
import com.wacajou.data.jpa.domain.Parcours;
import com.wacajou.data.jpa.domain.ParcoursInterface;
import com.wacajou.data.jpa.domain.User;

public class ParcoursForm implements ParcoursInterface{

	private Parcours parcours;

	private User respo;

	@Size(max = 50)
	private String name;

	@Size(max = 5000)
	private String description;

	private MultipartFile image;

	private Domain domain;

	@Size(max = 90)
	private String link;
	
	public ParcoursForm(){
		parcours = new Parcours();
	}
	
	public void setParcours(Parcours parcours){
		this.parcours = parcours;
	}
	
	public Parcours getParcours(){
		save();
		return this.parcours;
	}
	
	private void save(){
		if(description != null)
			parcours.setDescription(getDescription());
		if(domain != null)
			parcours.setDomain(getDomain());
		if(link != null)
			parcours.setLink(getLink());
		if(name != null)
			parcours.setName(getName());
		if(respo != null)
			parcours.setRespo(getRespo());
	}
	
	public String getDescription() {
		return this.description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	public User getRespo() {
		return this.respo;
	}

	public void setRespo(User user) {
		this.respo = user;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public MultipartFile getImage() {
		return image;
	}
	
	public void setImage(MultipartFile image) {
		this.image = image;		
	}

	public Domain getDomain() {
		return this.domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
