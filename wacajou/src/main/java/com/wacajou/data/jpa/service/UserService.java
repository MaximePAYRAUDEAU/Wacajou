package com.wacajou.data.jpa.service;

import org.hibernate.service.spi.ServiceException;

import com.wacajou.data.jpa.domain.User;

public interface UserService {
	void Create (String login, String promo, String statut) throws ServiceException;
	User Consult (String fname, String lname, String login, String mail) throws ServiceException;
	void Update (String fname, String lname, String mail) throws ServiceException;

}
