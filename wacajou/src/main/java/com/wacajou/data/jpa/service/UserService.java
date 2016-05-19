package com.wacajou.data.jpa.service;

import org.hibernate.service.spi.ServiceException;

public interface UserService {
	void Create (String login, String promo, String statut) throws ServiceException;
	

}
