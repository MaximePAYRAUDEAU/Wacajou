package com.wacajou.data.jpa.service;

import java.util.HashMap;
import java.util.List;

import org.hibernate.service.spi.ServiceException;

import com.wacajou.data.jpa.domain.Comments;
import com.wacajou.data.jpa.domain.User;
/**
 * 
 * @author Payraudeau Maxime
 *
 * @param <T> Class (Parcours, Module, User)
 */
public interface CommentService<T> {
	/**
	 * <h1>Get All comment from entity T
	 * 
	 * @param T Entity
	 * @return List of comments
	 * @throws ServiceException
	 */
	List<Comments> getComments(T t) throws ServiceException;
	
	/**
	 * <h1>Get last comment of entity T
	 * 
	 * @param T Entity
	 * @return Last comment
	 * @throws ServiceException
	 */
	Comments getLast(T t) throws ServiceException;
	
	/**
	 * <h1>Evaluate entity for one user 
	 * 
	 * @param T Entity
	 * @param User
	 * @param HashMap with all property
	 * @throws ServiceException
	 */
	void Evaluate(T t, User user, HashMap<String,Object> comment) throws ServiceException;
	
	/**
	 * <h1>Get comments for @param last days 
	 * @param T Entity
	 * @param Int days
	 * @return List of comment
	 * @throws ServiceException
	 */
	List<Comments> getLastDays(T t, int days) throws ServiceException;

	/**
	 * <h1>Get average mark for the entity
	 * 
	 * @param T Entity
	 * @param List of comments you want to evalutate 
	 * @return Float
	 * @throws ServiceException
	 */
	float getAverage(T t, List<Comments> comments) throws ServiceException;

	
}
