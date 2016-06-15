package com.wacajou.controller.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.Parcours;
import com.wacajou.data.jpa.domain.Statut;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.service.ModuleService;
import com.wacajou.data.jpa.service.ParcoursService;
import com.wacajou.data.jpa.service.UserService;

@Controller
@RequestMapping(value = "/administration", produces = "application/json; charset=UTF-8")
public class AdminController extends GenericModelAttribute{
	@Autowired
	private UserService userService;

	@Autowired
	private ParcoursService parcoursService;

	@Autowired
	private ModuleService moduleService;
	
	@RequestMapping("")
	public ModelAndView admin(@ModelAttribute("user") User user, ModelAndView modelAndView) {
		if (user.isConnect()) {
			if (!(user.getStatut().equals(Statut.ANCIEN) || user.getStatut().equals(Statut.STUDENT))){
				modelAndView.setViewName("admin/admin");
				settingModel(user, modelAndView);
			} else
				modelAndView.setViewName("home");
		} else
			modelAndView.setViewName("home");
		return modelAndView;
	}
	
	@RequestMapping("/ldr")
	public ModelAndView lettreDeRecommandation(@ModelAttribute("user")User user, ModelAndView modelAndView){
		
		modelAndView.setViewName("admin/user/ldr");
		return modelAndView;
	}
	
	private void settingModel(User user, ModelAndView modelAndView){
		if(user.getStatut().equals(Statut.RESPO_MODULE)){
			Module module = moduleService.getByRespo(user);
			modelAndView.addObject("Allmodule", module);
			modelAndView.addObject("Alluser", userService.getUserByModule(module));
		}else if(user.getStatut().equals(Statut.RESPO_PARCOURS)){
			Parcours parcours = parcoursService.getByRespo(user);
			List<Module> modules = moduleService.getByParcours(parcours);
			modelAndView.addObject("Allmodule", modules);
			modelAndView.addObject("Allparcours", parcours);
			modelAndView.addObject("Alluser", userService.getUserByParcours(parcours));
		}	
	}
}
