package com.wacajou.data.jpa.service;

import java.util.Date;

import org.hibernate.service.spi.ServiceException;

import com.wacajou.data.jpa.domain.User;

/**
 * <h1>Service pour la gestion des messages
 * 
 * @author Payraudeau Maxime
 *
 */
public interface MessageService {
	/**
	 * <h1>Envoie d'un message d'un utilisateur à un autre
	 * 
	 * @param user_sender
	 * @param user_reciver
	 * @param title
	 * @param subject
	 * @param message
	 * @throws ServiceException
	 */
	void Send(User user_sender, User user_reciver, String title, String subject, String message)
			throws ServiceException;

	/**
	 * <h1>Reception de l'ensemble des messages depuis le dernier log
	 * 
	 * @param user
	 * @param logDate
	 * @throws ServiceException
	 */
	void Recive(User user, Date logDate) throws ServiceException;

	/**
	 * <h1>Ensemble des messages recu par l'utilisateur
	 * 
	 * @param user_reciver
	 * @throws ServiceException
	 */
	void ReciveFrom(User user_reciver) throws ServiceException;

	/**
	 * <h1>Recupération des erreurs du service.
	 * 
	 * @return String error
	 */
	String getError();

}
