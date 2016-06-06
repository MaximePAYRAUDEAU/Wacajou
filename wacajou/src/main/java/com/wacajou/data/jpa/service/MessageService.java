package com.wacajou.data.jpa.service;

import java.util.Date;

import org.hibernate.service.spi.ServiceException;

import com.wacajou.data.jpa.domain.User;

public interface MessageService {
	void Send(User user_sender, User user_reciver, String title, String subject, String message) throws ServiceException;
	void Recive(User user, Date logDate) throws ServiceException;
	void ReciveFrom(User user_reciver) throws ServiceException;
	String getError();
	
}
