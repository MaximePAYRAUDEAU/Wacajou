package com.wacajou.data.jpa.service.impl;

import java.util.Date;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.wacajou.data.jpa.domain.Message;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.repository.MessageRepository;
import com.wacajou.data.jpa.service.MessageService;

@Component("messageService")
@Transactional
public class MessageServiceImpl implements MessageService {
	
	private String error = null;

	@Autowired
	private MessageRepository messageRepository;
		
	@Override
	public void Send(User user_sender, User user_reciver, String title,
			String subject, String message) throws ServiceException {
		Assert.notNull(user_sender);
		Assert.notNull(user_reciver);
		Assert.notNull(message);
		Message messageSend = new Message(user_sender, user_reciver, title, subject, message);
		try{
			messageRepository.save(messageSend);		
		}catch( Exception e ){
			error = "Message non envoyé";
			e.printStackTrace();
		}
	}

	@Override
	public void Recive(User user, Date logDate) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void ReciveFrom(User user_reciver) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getError() {
		return this.error;
	}

}
