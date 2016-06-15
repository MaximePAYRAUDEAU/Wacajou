package com.wacajou.controller.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
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

	/*@RequestMapping(value = "/test")
	public ModelAndView testing(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("home");
		User user = new User();
		user.Create("Test", "NONE", Statut.ADMIN);
		user.Complete("0000", "test_firstname", "test_lastname",
				"test@isep.fr", "TEST");
		session.setAttribute("session_user", user);
		return modelAndView;
	}*/

	@RequestMapping(value = "/sidebar")
	public ModelAndView sidebar(ModelAndView modelAndView) {
		modelAndView.setViewName("template/sidebar");
		return modelAndView;
	}
	
	@RequestMapping(value = "/profil")
	public ModelAndView profilPage(@ModelAttribute("user") User user, ModelAndView modelAndView) {
		if (user.isConnect()) {
			modelAndView.setViewName("user/profil");
			modelAndView.addObject("reasons", Reason.values());
		}else
			modelAndView.setViewName("home");
		return modelAndView;
	}
	
}
