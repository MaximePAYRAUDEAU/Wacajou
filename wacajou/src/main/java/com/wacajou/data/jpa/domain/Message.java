package com.wacajou.data.jpa.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class Message extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	
	@OneToOne(fetch = FetchType.LAZY, targetEntity = User.class, optional = true)
	private User userReciver;
	@OneToOne(fetch = FetchType.LAZY, targetEntity = User.class, optional = true)
	private User userSender;
	
	@Column(name = "message", nullable = false, length = 5000)
	private String message;
	
	@Column(name = "title", nullable = true, length = 50)
	private String title;
	
	@Column(name = "subject", nullable = true, length = 50)
	private String subject;
	
	@Column(name = "logdate")
	private Date logdate;
	
	protected Message(){
		
	}
	
	public Message(User sender, User reciver, String title, String subject, String message){
		this.userSender = sender;
		this.userReciver = reciver;
		this.title = title;
		this.subject = subject;
		this.message = message;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public String getSubject(){
		return this.subject;
	}
	
	public String getMessage(){
		return this.message;
	}
	
	public Date getLogdate(){
		return this.logdate;
	}
	
	@PrePersist
	@PreUpdate
	public void setLogDate(){
		this.logdate = new Date();
	}
	
	@Override
	public String toString(){
		return getTitle() + "," + getSubject() + "," + getMessage();
	}
}