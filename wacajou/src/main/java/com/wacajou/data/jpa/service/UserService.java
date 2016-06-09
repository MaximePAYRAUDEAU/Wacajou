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

	User Connect(String login, String mdp) throws ServiceException;
	
	User ConnexionAlwayTrue(String login) throws ServiceException;

	User getUser(String user) throws ServiceException;

	User getUser(long user) throws ServiceException;
	
	User getUserByLogin(String login) throws ServiceException;

	Parcours getUserParcours(User user) throws ServiceException;

	List<Module> getUserModule(User user) throws ServiceException;

	UserInfo getInfos(User user) throws ServiceException;

	void updateInfo(User user, String full_file_name, String file_name)
			throws ServiceException;

	List<User> getAllUser() throws ServiceException;

	String getError();

}
