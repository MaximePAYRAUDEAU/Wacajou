package com.wacajou.controller.admin.impl;

import com.wacajou.controller.common.GenericModelAttribute;
import com.wacajou.data.jpa.domain.Statut;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.service.UserService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

public abstract class AdminModelAttribute extends GenericModelAttribute {

	public static final String ALL_USER = GenericModelAttribute.ALL_USER;
	public static final String LIST_ELEVE = "ListEleve";
	public static final String LIST_ANCIEN = "ListAncien";
	public static final String LIST_RESPO_MODULE = "ListRespoModule";
	public static final String LIST_RESPO_PARCOURS = "ListRespoParcours";
	public static final String LIST_RESPO_PEDAGOGIQUE = "ListRespoPedagogique";
	
	public static final String ALL_MODULE = GenericModelAttribute.ALL_MODULE;
	
	@ModelAttribute(LIST_ELEVE)
	public List<User> listUserEleve(@ModelAttribute(ALL_USER) List<User> listUsers) {
		List<User> listReturn = new ArrayList<User>();
		for (User user: listUsers)
			if (user.getStatut().equals(Statut.STUDENT))
				listReturn.add(user);
		return listReturn;
	}
	
	@ModelAttribute(LIST_ANCIEN)
	public List<User> listUserAncien(@ModelAttribute(ALL_USER) List<User> listUsers) {
		List<User> listReturn = new ArrayList<User>();
		for (User user: listUsers)
			if (user.getStatut().equals(Statut.ANCIEN))
				listReturn.add(user);
		return listReturn;
	}
	
	@ModelAttribute(LIST_RESPO_MODULE)
	public List<User> listUserRespoModule(@ModelAttribute(ALL_USER) List<User> listUsers) {
		List<User> listReturn = new ArrayList<User>();
		for (User user: listUsers)
			if (user.getStatut().equals(Statut.RESPO_MODULE))
				listReturn.add(user);
		return listReturn;
	}

	@ModelAttribute(LIST_RESPO_PARCOURS)
	public List<User> listUserRespoParcours(@ModelAttribute(ALL_USER) List<User> listUsers) {
		List<User> listReturn = new ArrayList<User>();
		for (User user: listUsers)
			if (user.getStatut().equals(Statut.RESPO_PARCOURS))
				listReturn.add(user);
		return listReturn;
	}

	@ModelAttribute(LIST_RESPO_PEDAGOGIQUE)
	public List<User> listUserRespoPedagogique(@ModelAttribute(ALL_USER) List<User> listUsers) {
		List<User> listReturn = new ArrayList<User>();
		for (User user: listUsers)
			if (user.getStatut().equals(Statut.RESPO_PEDAGOGIQUE))
				listReturn.add(user);
		return listReturn;
	}

	
}
