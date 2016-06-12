package com.wacajou.data.jpa.service;

import java.util.HashMap;
import java.util.List;

import org.hibernate.service.spi.ServiceException;

import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.User;

public abstract interface GenericService<T> {
	/**
	 * <h1>Generic create method for any services
	 * 
	 * @param HashMap with all attribute needed
	 * @throws ServiceException
	 */
	void Create(HashMap<String,Object> map) throws ServiceException;
	
	/**
	 * <h1>Generic update method for any services
	 * 
	 * @param HashMap with all attribute needed
	 * @param T Entity that you want to update
	 * @throws ServiceException
	 */
	void Update(T t, HashMap<String,Object> map) throws ServiceException;
	
	/**
	 * <h1>Generic delete method for any services
	 * 
	 * @param T Entity that you want to delete
	 * @throws ServiceException
	 */
	void Delete(T t) throws ServiceException;
	
	/**
	 * <h1>Generic method to get an entity by his responsable
	 * 
	 * @param user
	 * @return T Entity type
	 * @throws ServiceException
	 */
	T getByRespo(User user) throws ServiceException;
	
	/**
	 * <h1>Generic method to get an entity by his name
	 * 
	 * @param name
	 * @return T Entity type
	 * @throws ServiceException
	 */
	T getByName(String name) throws ServiceException;
	
	/**
	 * <h1>Generic method to get all entity
	 * 
	 * @return List<T> Entity type
	 * @throws ServiceException
	 */
	List<T> getAll() throws ServiceException;
	
	T getOne(Long id) throws ServiceException;

	/**
	 * <h1>Generic error return
	 * 
	 * @return null or String if error happened
	 */
	String getError();
}
