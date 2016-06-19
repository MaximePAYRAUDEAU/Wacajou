package com.wacajou.controller.admin.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.wacajou.controller.common.GenericModelAttribute;
import com.wacajou.data.jpa.domain.Statut;
import com.wacajou.data.jpa.domain.User;

public abstract class AdminModelAttribute extends GenericModelAttribute {

	public static final String ALL_USER = GenericModelAttribute.ALL_USER;
	public static final String LIST_ELEVE = "ListEleve";
	public static final String LIST_ANCIEN = "ListAncien";
	public static final String LIST_RESPO_MODULE = "ListRespoModule";
	public static final String LIST_RESPO_PARCOURS = "ListRespoParcours";
	public static final String LIST_RESPO_PEDAGOGIQUE = "ListRespoPedagogique";
	public static final String ALL_MODULE = GenericModelAttribute.ALL_MODULE;
	
	@ModelAttribute(LIST_ELEVE)
	public @ResponseBody List<User> listUserEleve(@ModelAttribute(ALL_USER) List<User> listUsers) {
		return listFromStatus(listUsers, Statut.STUDENT);
	}
	
	@ModelAttribute(LIST_ANCIEN)
	public @ResponseBody List<User> listUserAncien(@ModelAttribute(ALL_USER) List<User> listUsers) {
		return listFromStatus(listUsers, Statut.ANCIEN);
	}
	
	@ModelAttribute(LIST_RESPO_MODULE)
	public @ResponseBody List<User> listUserRespoModule(@ModelAttribute(ALL_USER) List<User> listUsers) {
		return listFromStatus(listUsers, Statut.RESPO_MODULE);
	}

	@ModelAttribute(LIST_RESPO_PARCOURS)
	public @ResponseBody List<User> listUserRespoParcours(@ModelAttribute(ALL_USER) List<User> listUsers) {
		return listFromStatus(listUsers, Statut.RESPO_PARCOURS);
	}

	@ModelAttribute(LIST_RESPO_PEDAGOGIQUE)
	public @ResponseBody List<User> listUserRespoPedagogique(@ModelAttribute(ALL_USER) List<User> listUsers) {
		return listFromStatus(listUsers, Statut.RESPO_PEDAGOGIQUE);
	}
	
	private List<User> listFromStatus(List<User> listUsers, Statut Statut){
		List<User> listReturn = new ArrayList<User>();
		for (User user: listUsers)
			if (user.getStatut().equals(Statut))
				listReturn.add(user);
		return listReturn;
	}
	
}
