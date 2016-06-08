package com.wacajou.data.jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
/**
 * 
 * @author Payraudeau Maxime
 *
 */
@Entity
public class LettreDeRecommandation extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@OneToOne(fetch = FetchType.LAZY, targetEntity = User.class, optional = true)
	private User userSender;

	@OneToOne(fetch = FetchType.LAZY, targetEntity = User.class, optional = true)
	private User userReciver;

	@Column(name = "path", nullable = true)
	private String path;

	@Column(name = "statut", nullable = false)
	private LDRStatut statut;
	
	@Column(name = "reason", nullable = false)
	private Reason reason;
	
	@Column(name = "comment", nullable = true)
	private String comment;

	public void setUserSender(User user) {
		this.userSender = user;
	}

	public void setUserReciver(User user) {
		this.userReciver = user;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setStatut(LDRStatut statut) {
		this.statut = statut;
	}
	
	public void setReason(Reason reason){
		this.reason = reason;
	}
	
	public void setComment(String comment){
		this.comment = comment;
	}
	
	public User getSender(){
		return this.userSender;
	}
	
	public User getReciver(){
		return this.userReciver;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public LDRStatut getStatut(){
		return this.statut;
	}
	
	public Reason getReason(){
		return this.reason;
	}
	
	public String getComment(){
		return this.comment;
	}
	
	@Override
	public String toString(){
		return "Id=" + getId() + ",Path=" + getPath() + ",Statut=" + getStatut(); 
	}
	
}
