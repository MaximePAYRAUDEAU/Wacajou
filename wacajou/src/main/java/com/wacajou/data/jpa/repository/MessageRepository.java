package com.wacajou.data.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wacajou.data.jpa.domain.Message;
import com.wacajou.data.jpa.domain.User;

public interface MessageRepository extends JpaRepository<Message, Long> {
	
	Page<Message> findAll(Pageable page);
	
	Page<Message> findByUserSender(User user, Pageable page);
	
	Page<Message> findByUserReciver(User user, Pageable page);
	
	Page<Message> findByUserSenderAndUserReciver(User user_sender, User user_reciver, Pageable page);
	
	
} 
