package com.wacajou.controller.common;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wacajou.data.jpa.domain.Reason;
import com.wacajou.data.jpa.domain.Statut;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.service.ModuleService;
import com.wacajou.data.jpa.service.ParcoursService;
import com.wacajou.data.jpa.service.UserService;

/**
 * Class GlobalController that assure homepage wacajou application.
 * 
 * @author Payraudeau Maxime
 *
 */
@Controller
public class GlobalController extends GenericModelAttribute{
	protected static final String SESSION_USER = "session_user";

	@Autowired
	private UserService userService;

	@Autowired
	private ParcoursService parcoursService;

	@Autowired
	private ModuleService moduleService;
	
	@RequestMapping(value = "/")
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:home");
		return modelAndView;
	}
	
	@RequestMapping(value = "/home")
	public ModelAndView goHome() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("home");
		return modelAndView;
	}

	@RequestMapping(value = "/test")
	public ModelAndView testing(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("home");
		User user = new User();
		user.Create("Test", "NONE", Statut.ADMIN);
		user.Complete("0000", "test_firstname", "test_lastname",
				"test@isep.fr", "TEST");
		session.setAttribute("session_user", user);
		return modelAndView;
	}

	@RequestMapping(value = "/administration")
	public ModelAndView admin(@ModelAttribute("user") User user, ModelAndView modelAndView) {
		if (user.isConnect()) {
			if (!(user.getStatut().equals(Statut.ANCIEN) || user.getStatut()
					.equals(Statut.STUDENT)))
				modelAndView.setViewName("admin/admin");
			else
				modelAndView.setViewName("home");
		} else
			modelAndView.setViewName("home");
		return modelAndView;
	}

	@RequestMapping(value = "/sidebar")
	public ModelAndView sidebar(ModelAndView mandv) {
		mandv.setViewName("template/sidebar");
		return mandv;
	}
	
	@RequestMapping(value = "/profil")
	public ModelAndView profilPage(@ModelAttribute("user") User user, ModelAndView mandv) {
		if (user.isConnect()) {
			mandv.setViewName("user/profil");
			mandv.addObject("reasons", Reason.values());
		}else
			mandv.setViewName("home");
		return mandv;
	}
	
}
