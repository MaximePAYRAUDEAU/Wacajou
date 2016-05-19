package com.wacajou.module.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/user/add")
	public ModelAndView addUserPage(){
		ModelAndView mandv = new ModelAndView();
		mandv.setViewName("user/add-user-form");
		mandv.addObject("user", new User());
		return mandv;
	}
	
	@RequestMapping(value="/user/add/process", method=RequestMethod.POST)
	public ModelAndView addingUser(@PathVariable String login, @PathVariable String promo, @PathVariable String statut){
		ModelAndView mandv = new ModelAndView("home");
		userService.Create(login, promo, statut);
		String message = "User was succesfully added.";
		mandv.addObject(message, message);
		return mandv;
		
	
	}

	
}
