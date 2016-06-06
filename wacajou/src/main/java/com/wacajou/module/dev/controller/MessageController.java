package com.wacajou.module.dev.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.service.MessageService;
import com.wacajou.data.jpa.service.UserService;

/**
 * Class ConnexionController that assure connexion in wacajou application.
 * 
 * @author Payraudeau Maxime
 *
 */
@Controller
@RequestMapping("/message")
public class MessageController {

	@Autowired
	private MessageService messageService;

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/*", method = RequestMethod.GET)
	public ModelAndView setMessage(){
		ModelAndView mandv = new ModelAndView();
		mandv.setViewName("message/send");
		List<User> users = userService.getAllUser();
		mandv.addObject("users", users);
		return mandv;
	}
	
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public ModelAndView sendMessage(
			@RequestParam(required = true) String title,
			@RequestParam(required = true) String message,
			@RequestParam(required = false) String subject,
			@RequestParam(required = true) User user_reciver,
			HttpSession session, ModelAndView modelAndView) {
		User user_sender = (User) session.getAttribute("user_session");
		messageService.Send(user_sender, user_reciver, title, subject, message);
		String messageReturn = "";
		if(	messageService.getError() == null )
			messageReturn = "Message envoyé avec succés";
		else
			messageReturn = messageService.getError();
		modelAndView.addObject("message", messageReturn);
		return modelAndView;
	}

}
