package com.wacajou.controller.common;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.wacajou.data.jpa.domain.Domain;
import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.Parcours;
import com.wacajou.data.jpa.domain.Statut;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.domain.UserInfo;
import com.wacajou.data.jpa.service.ModuleService;
import com.wacajou.data.jpa.service.ParcoursService;
import com.wacajou.data.jpa.service.UserService;

@SessionAttributes( value = {"user", "userInfo", "userParcours", "userModule" }, types = {User.class, UserInfo.class, Parcours.class, Module.class})
public abstract class GenericModelAttribute {
	
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
	
	/*@ModelAttribute("user")
	public User user(HttpSession session){
		if(session.getAttribute(SESSION_USER) != null)
			return (User) session.getAttribute(SESSION_USER);
		return null;
	}*/
	
	/*@ModelAttribute("userInfo")
	public UserInfo infoUser(@ModelAttribute("user") User user){
		UserInfo userInfo;
		if(user.isConnect())
			System.out.println("User connected ! ");
		else
			System.out.println("Not connected !");
		if(user.isConnect())
			return userService.getInfos(user);
		else{
			userInfo = new UserInfo();
			userInfo.setActivities("Activities");
			userInfo.setCv("Cv");
			userInfo.setInternship("Stage");
			userInfo.setLdm("Lettre de motivation");
			userInfo.setMark("Notes");
			userInfo.setUniversity("Université");
		}
		return userInfo;
	}*/
	
	/*@ModelAttribute("userParcours")
	public Parcours parcoursUser(@ModelAttribute("user") User user){
		if (user.isConnect()) 
			return userService.getUserParcours(user);
		return null;
	}

	@ModelAttribute("userModules")
	public List<Module> moduleUser(@ModelAttribute("user") User user){
		if (user.isConnect()) 
			return userService.getUserModule(user);
		return null;
	}*/
	
	@ModelAttribute("userRight")
	public Statut rightUser(@ModelAttribute("user") User user){
		if (user.isConnect())
			if (!(user.getStatut().equals(Statut.STUDENT) || user.getStatut()
					.equals(Statut.ANCIEN)))
				return user.getStatut();
		return null;
	}
	
	@ModelAttribute("RespoParcours")
	public Statut rightParcours(){
		return Statut.RESPO_PARCOURS;
	}

	@ModelAttribute("RespoModule")
	public Statut rightModule(){
		return Statut.RESPO_MODULE;
	}
	@ModelAttribute("RespoPedagogique")
	public Statut rightPedagogique(){
		return Statut.RESPO_PEDAGOGIQUE;
	}

	@ModelAttribute("Domain")
	public Domain[] domain(){
		return Domain.values();
	}
}
