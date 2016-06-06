package com.wacajou.module.dev.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.Parcours;
import com.wacajou.data.jpa.domain.Statut;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.domain.UserInfo;
import com.wacajou.data.jpa.service.ParcoursService;
import com.wacajou.data.jpa.service.UserService;




/**
 * Class GlobalController that assure homepage wacajou application.
 * 
 * @author Payraudeau Maxime
 *
 */
@Controller
public class GlobalController {
	protected static final String SESSION_USER = "session_user";

	@Autowired
	private UserService userService;
	
	@Autowired
	private ParcoursService parcoursService;
	
	@RequestMapping("/home")
	public ModelAndView goHome(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("home");
		System.out.println("Home called");
		modelAndView = setPortFolio(null, modelAndView);
		return modelAndView;
	}
	
	@RequestMapping("/test")
	public ModelAndView testing(HttpSession session){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("home");
		User user = new User();
		user.Create("Test", "NONE", Statut.ADMIN);
		user.Complete("0000", "test_firstname", "test_lastname", "test@isep.fr", "TEST");
		session.setAttribute("user_session", user);
		modelAndView = setPortFolio(null, modelAndView);
		return modelAndView;
	}
	
	@RequestMapping("/admin")
	public ModelAndView administration(HttpSession session){
		ModelAndView modelAndView = new ModelAndView();
		User user = (User) session.getAttribute(SESSION_USER);
		if(user != null){
			if(!(user.getStatut().equals(Statut.ANCIEN) || user.getStatut().equals(Statut.STUDENT)))
				modelAndView.setViewName("admin/admin");
			else
				modelAndView.setViewName("redirect:/home");
		}else
			modelAndView.setViewName("redirect:/home");
		return modelAndView;
	}
	
	@RequestMapping("/sidebar")
	public ModelAndView profilPage(HttpSession session) {
		
		ModelAndView mandv = new ModelAndView();
		mandv.setViewName("/template/sidebar");
		
		User user = (User) session.getAttribute("session_user");
		
		Statut right = null;
		UserInfo userInfo = null;
		Parcours parcours = null;
		List<Module> modules = null;
		
		if(user != null){
	
			if(!(user.getStatut().equals(Statut.STUDENT) || user.getStatut().equals(Statut.ANCIEN)) )
				right = user.getStatut();
		
			userInfo = userService.getInfos(user);
			parcours = userService.getUserParcours(user);
			modules = userService.getUserModule(user);
		}
		
		mandv.addObject("user", user);
		mandv.addObject("userInfo", userInfo);
		mandv.addObject("right", right);
		mandv.addObject("parcours", parcours);
		mandv.addObject("modules", modules);

		return mandv;
	}
	
	private ModelAndView setPortFolio(User user, ModelAndView modelAndView){
		if(user == null){
			List<Parcours> listParcours = parcoursService.getAll();
			modelAndView.addObject("parcours", listParcours);
		}
		return modelAndView;
	}
	
}