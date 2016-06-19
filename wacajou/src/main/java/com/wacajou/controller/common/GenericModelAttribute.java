package com.wacajou.controller.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.wacajou.data.jpa.domain.Domain;
import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.Parcours;
import com.wacajou.data.jpa.domain.Reason;
import com.wacajou.data.jpa.domain.Statut;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.domain.UserInfo;
import com.wacajou.data.jpa.service.ModuleService;
import com.wacajou.data.jpa.service.ParcoursService;
import com.wacajou.data.jpa.service.UserService;

@SessionAttributes( value = {"user", "userInfo", "userParcours", "userModule", "userRight", "responsability"}, types = {User.class, UserInfo.class, Parcours.class, Module.class})
public abstract class GenericModelAttribute {
	public static final String ALL_USER = "Alluser";
	public static final String ALL_MODULE = "Allmodule";
	public static final String ALL_PARCOURS = "Allparcours";
	
	public static final String RESPO_MODULE = "RespoModule";
	public static final String RESPO_PARCOURS = "RespoParcours";
	public static final String RESPO_PEDAGOGIQUE = "RespoPedagogique";	
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private ParcoursService parcoursService;
	@Autowired
	private UserService userService;
	
	/* Get all of each Entity*/	
	@ModelAttribute(ALL_MODULE)
	public List<Module> modules() {
		return moduleService.getAll();
	}

	@ModelAttribute(ALL_PARCOURS)
	public List<Parcours> parcours() {
		return parcoursService.getAll();
	}

	@ModelAttribute(ALL_USER)
	public List<User> users() {
		return userService.getAll();
	}
	
	/* Set userRight*/ 	
	@ModelAttribute(RESPO_MODULE)
	public Statut rightParcours(){
		return Statut.RESPO_PARCOURS;
	}

	@ModelAttribute(RESPO_PARCOURS)
	public Statut rightModule(){
		return Statut.RESPO_MODULE;
	}
	@ModelAttribute(RESPO_PEDAGOGIQUE)
	public Statut rightPedagogique(){
		return Statut.RESPO_PEDAGOGIQUE;
	}

	@ModelAttribute("Domain")
	public Domain[] domain(){
		return Domain.values();
	}
	
	@ModelAttribute("Reasons")
	public Reason[] reaseon(){
		return Reason.values();
	}
}
