package com.wacajou.module.dev.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.wacajou.data.jpa.domain.Reason;
import com.wacajou.data.jpa.domain.Statut;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.service.UserService;

@Controller
@SessionAttributes(value = UserController.SESSION_USER, types = { User.class })
public class UserController {
	protected static final String SESSION_USER = "session_user";

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/user/add")
	public ModelAndView addUserPage() {
		ModelAndView mandv = new ModelAndView();
		mandv.setViewName("admin/user/create");
		Statut[] statut = Statut.values();
		mandv.addObject("statut", statut);
		return mandv;
	}
	
	@RequestMapping(value = "/profil")
	public ModelAndView profilPage(HttpSession session) {
		ModelAndView mandv = new ModelAndView();
		mandv.setViewName("/user/profil");
		User user = (User) session.getAttribute(SESSION_USER);
		System.out.println("user : " + user.getLogin());
		mandv.addObject("reasons", Reason.values());
		mandv.addObject("user", user);
		return mandv;
	}

	/**
	 * <h1>Add new user to our database that allow him to connect
	 * 
	 * @param login
	 * @param promo
	 * @param statut
	 * @param modelAndView
	 * @return modelAndView
	 */
	@RequestMapping(value = "user/add/process", method = RequestMethod.POST)
	public ModelAndView addingUser(
			@RequestParam("login") String login, 
			@RequestParam("promo") String promo,
			@RequestParam("statut") String statut, 
			ModelAndView modelAndView) {
		userService.Create(login, promo, statut);
		modelAndView.setViewName("admin/user/create");
		if(userService.getError() != null)
			modelAndView.addObject("success", "User was succesfully added.");
		else
			modelAndView.addObject("error", userService.getError());
		return modelAndView;
	}

}
