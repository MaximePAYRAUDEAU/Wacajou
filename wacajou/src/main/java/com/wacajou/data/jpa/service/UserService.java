package com.wacajou.data.jpa.service;

import java.util.List;

import org.hibernate.service.spi.ServiceException;

import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.Parcours;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.domain.UserInfo;

public interface UserService {
	void Create(String login, String promo, String statut)
			throws ServiceException;

	User Connexion(String login, String mdp) throws ServiceException;

	String getError();

	User getUser(String user_reciver_name) throws ServiceException;

	User getUser(long user_id) throws ServiceException;
	
	User getUserByLogin(String string) throws ServiceException;

	List<User> getAllUser() throws ServiceException;

	User ConnexionAlwayTrue(String login) throws ServiceException;

	UserInfo getInfos(User user) throws ServiceException;

	Parcours getUserParcours(User user) throws ServiceException;

	List<Module> getUserModule(User user) throws ServiceException;

	

}
