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
public class User extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@Column(nullable = true, length = 10)
	private String number;
	
	@Column(nullable = true, length = 30)
	private String fname;
	
	@Column(nullable = true, length = 30)
	private String lname;
	
	@Column(nullable = true, length = 10)
	private String promo;
	
	@Column(nullable = false)
	private Statut statut;
	
	@Column(nullable = true, length = 100)
	private String mail;
	
	@Column(nullable = false, unique = true, length = 50)
	private String login;
	
	@Column(nullable = true, length = 30)
	private String type;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<UserModule> userModule;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
	private UserParcours userParcours;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<Comments> comments;

	public User() {

	}

	public void Create(String login, String promo, Statut statut) {
		this.login = login;
		this.statut = statut;
		this.promo = promo;
	}

	public void Complete(String number, String fname, String lname,
			String mail, String type) {
		this.number = number;
		this.fname = fname;
		this.lname = lname;
		this.mail = mail;
		this.type = type;
	}

	public boolean isConnect(){
		if(login != null )
			return true;
		return false;
	}
	
	public String getFname() {
		return this.fname;
	}

	public String getLname() {
		return this.lname;
	}

	public Statut getStatut() {
		return this.statut;
	}

	public String getMail() {
		return this.mail;
	}

	public Set<Comments> getComms() {
		return this.comments;
	}

	public UserParcours getParcours(){
		return this.userParcours;
	}
	
	public String getLogin() {
		return this.login;
	}

	public String getNumber() {
		return this.number;
	}
	
	public String getPromo() {
		return this.promo;
	}

	@Override
	public String toString() {
		return getId() + "," + getLogin() + "," + getFname() + "," + getLname()
				+ "," + getMail() + "," + getStatut();
	}

	
}