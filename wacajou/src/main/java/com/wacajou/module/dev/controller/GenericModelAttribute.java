package com.wacajou.module.dev.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.Parcours;
import com.wacajou.data.jpa.domain.Statut;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.domain.UserInfo;
import com.wacajou.data.jpa.service.ModuleService;
import com.wacajou.data.jpa.service.ParcoursService;
import com.wacajou.data.jpa.service.UserService;

public abstract class GenericModelAttribute {
	protected static final String SESSION_USER = "session_user";
	
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private ParcoursService parcoursService;
	@Autowired
	private UserService userService;
	
	@ModelAttribute("Allmodule")
	public List<Module> modules() {
		return moduleService.getAll();
	}

	@ModelAttribute("Allparcours")
	public List<Parcours> parcours() {
		return parcoursService.getAll();
	}

	@ModelAttribute("Alluser")
	public List<User> users() {
		return userService.getAllUser();
	}
	
	@ModelAttribute("user")
	public User user(HttpSession session){
		if(session.getAttribute(SESSION_USER) != null)
			return (User) session.getAttribute(SESSION_USER);
		return null;
	}
	
	@ModelAttribute("userInfo")
	public UserInfo infoUser(HttpSession session){
		if(session.getAttribute(SESSION_USER) != null)
			return userService.getInfos((User) session.getAttribute(SESSION_USER));
		return null;
	}
	@ModelAttribute("userParcours")
	public Parcours parcoursUser(HttpSession session){
		if (session.getAttribute(SESSION_USER) != null) 
			return userService.getUserParcours((User) session.getAttribute(SESSION_USER));
		return null;
	}

	@ModelAttribute("userModules")
	public List<Module> moduleUser(HttpSession session){
		if (session.getAttribute(SESSION_USER) != null) 
			return userService.getUserModule((User) session.getAttribute(SESSION_USER));
		return null;
	}
	
	@ModelAttribute("userRight")
	public Statut rightUser(HttpSession session){
		if ( session.getAttribute(SESSION_USER) != null) {
			User user = (User) session.getAttribute(SESSION_USER);
			if (!(user.getStatut().equals(Statut.STUDENT) || user.getStatut()
					.equals(Statut.ANCIEN)))
				return user.getStatut();
		}
		return null;
	}
}
