package com.wacajou.module.dev.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.service.UserService;

/**
 * Class ConnexionController that assure connexion in wacajou application.
 * 
 * @author Payraudeau Maxime
 *
 */
@Controller
@SessionAttributes(value = ConnexionController.SESSION_USER, types = { User.class })
public class ConnexionController {

	protected static final String SESSION_USER = "session_user";
	
	@Autowired
	private UserService userService;

	@RequestMapping("/login")
	public String connexion(Model model) {
		return "user/login";
	}

	/**
	 * Get POST method from /login Url that have @param login and @param mdp
	 * value Send to the view a message if successfully log on the application
	 * 
	 * @param login
	 * @param mdp
	 * @param model
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void connexion(
			@RequestParam(value = "login", required = true) String login,
			@RequestParam(value = "mdp", required = true) String mdp, HttpSession session,
			Model model) {
		User user = userService.Connexion(login, mdp);
		if (userService.getError() == null) {
			model.addAttribute("message", "Request successfully done !");
			session.setAttribute(SESSION_USER, user);
			return;
		} else
			model.addAttribute("message", "Fail to log on.");
		return;
	}
	
	@RequestMapping(value = "/login/test", method = RequestMethod.POST)
	public ModelAndView connexionTest(
			@RequestParam(value = "login", required = true) String login,
			@RequestParam(value = "mdp", required = true) String mdp, HttpSession session,
			ModelAndView modelAndView) {
		User user = userService.ConnexionAlwayTrue(login);
		if (userService.getError() == null) {
			modelAndView.addObject("message", "Request successfully done !");
			session.setAttribute(SESSION_USER, user);
			modelAndView.setViewName("forward:../profil");
		} else {
			modelAndView.addObject("message", "Fail to log on.");
			modelAndView.setViewName("redirect:../home");
		}
		return modelAndView;
	}

	/**
	 * Get any method from /logout Url. Unlog user and return him to home page
	 * 
	 * @return Home page
	 */
	@RequestMapping(value = "/logout")
	public ModelAndView deconnexion(SessionStatus session, ModelAndView modelAndView) {
		session.setComplete();
		modelAndView.setViewName("home");
		return modelAndView;
	}
}
