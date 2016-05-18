package com.wacajou.data.jpa.domain;

/**
 * @author Payraudeau Maxime
 * 
 */
import java.io.Serializable;
import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable, Cloneable {

	private static final long serialVersionUID = 3262688615981935345L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Version
	private int version;
	
	public Long getId(){
		return this.id;
	}
	
	protected void setId(Long id){
		this.id = id;
	}
	
	public void setVersion(int version){
		this.version = version;
	}
	
	public int getVersion(){
		return this.version;
	}
	
	@Override
	public boolean equals(Object obj){
		if(this == obj){
			return true;
		}
		if(this.id == null){
			return false;
		}
		if(obj instanceof AbstractEntity && obj.getClass().equals(getClass())){
			return this.id.equals(((AbstractEntity) obj).id);
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		int hash = 5;
		hash = 43 * hash + Objects.hashCode(this.id);
		return hash;
	}
}