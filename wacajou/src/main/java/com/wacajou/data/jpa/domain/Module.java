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

	@Column(name = "name", unique = true, nullable = false)
	private String name;

	@OneToOne(fetch = FetchType.LAZY, targetEntity = User.class, optional = true)
	private User respo;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "module")
	private Set<UserModule> userModule;

	@Column(name = "description", nullable = true, length = 5000)
	private String description;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "module")
	private Set<Comments> comments;

	@Column(name = "domain", nullable = false)
	private Domain domain;

	@Column(name = "image", nullable = true, length = 50)
	private String image;

	@Column(name = "code", nullable = false)
	private String code;

	@Column(name = "semester", nullable = true)
	private String semester;
	
	@Column(name = "tp_cours", nullable = true)
	private int tp_cours;
	
	@Column(name = "project", nullable = true)
	private int project;
	
	@Column(name = "ECTS", nullable = true)
	private double ects;
	
	@Column(name = "link", nullable = true)
	private String link;
	
	@Column(name = "statut", nullable = false)
	private Status status;
	
	public Module() {

	}

	public void Create(String name, String description, String image, String code, Domain domain) {
		this.name = name;
		this.description = description;
		this.domain = domain;
		this.image = image;
		this.code = code;
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
	
	public void setImage(String image){
		this.image = image;
	}
	
	public String getImage() {
		return this.image;
	}
	
	public void setCode(String code){
		this.code = code;
	}
	
	public String getCode(){
		return this.code;
	}
	
	public void setTpCours(int tp_cours){
		this.tp_cours = tp_cours;
	}
	
	public int getTpCours(){
		return this.tp_cours;
	}

	public void setProject(int project){
		this.project = project;
	}
	
	public int getProject(){
		return this.project;
	}
	
	public void setSemester(String semester){
		this.semester = semester;
	}

	public String getSemester(){
		return this.semester;
	}
	
	public void setEcts(double ects){
		this.ects = ects;
	}
	
	public double getEcts(){
		return this.ects;
	}
	
	public void setLink(String link){
		this.link = link;
	}

	public String getLink(){
		return this.link;
	}
}