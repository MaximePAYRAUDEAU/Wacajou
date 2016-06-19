package com.wacajou.controller.admin.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
public class AdminController extends AdminModelAttribute{
	@Autowired
	private UserService userService;

	@Autowired
	private ParcoursService parcoursService;

	@Autowired
	private ModuleService moduleService;
	
	@RequestMapping("/administration")
	public ModelAndView admin(@ModelAttribute(AdminModelAttribute.ALL_USER)List<User> listUser, @ModelAttribute("user") User user, ModelAndView modelAndView) {
		modelAndView.setViewName("admin/admin");
				settingModel(listUser, user, modelAndView);
		return modelAndView;
	}
	
	@RequestMapping("/ldr")
	public ModelAndView lettreDeRecommandation(@ModelAttribute("user")User user, ModelAndView modelAndView){
		
		modelAndView.setViewName("admin/user/ldr");
		return modelAndView;
	}
	
/*	@RequestMapping("/{type}/create")
	public ModelAndView create(@PathVariable("type") String type, ModelAndView modelAndView){
		if(type.equals("module"))
			modelAndView.addObject("Module", new Module());
		else if(type.equals("parcours"))
			modelAndView.addObject("Parcours", new Parcours());
		else if(type.equals("user"))
			modelAndView.addObject("User", new User());
		else {
			modelAndView.setViewName("redirect:../../");
			return modelAndView;
		}
		modelAndView.setViewName("admin/" + type + "/create");
		return modelAndView;
	}
	
	@RequestMapping("/module/create/process")
	public ModelAndView createModuleProcess(@ModelAttribute("Module")Module module, ModelAndView modelAndView){
		
		modelAndView.setViewName("forward:../../administration");
		return modelAndView;
	}
	
	@RequestMapping("/module/create/process")
	public ModelAndView createModuleProcess(@ModelAttribute("Module")Module module, ModelAndView modelAndView){
		
		modelAndView.setViewName("forward:../../administration");
		return modelAndView;
	}
	
	@RequestMapping("/module/create/process")
	public ModelAndView createModuleProcess(@ModelAttribute("Module")Module module, ModelAndView modelAndView){
		
		modelAndView.setViewName("forward:../../administration");
		return modelAndView;
	}*/
	
	private void settingModel(List<User> listUser, User user, ModelAndView modelAndView){
		List<Integer> years = new ArrayList<Integer>();
		if(user.getStatut().equals(Statut.RESPO_MODULE)){
			Module module = moduleService.getByRespo(user);
			listUser = userService.getUserByModule(module);
						modelAndView.addObject("Allmodule", module);
		}else if(user.getStatut().equals(Statut.RESPO_PARCOURS)){
			Parcours parcours = parcoursService.getByRespo(user);
			List<Module> modules = moduleService.getByParcours(parcours);
			listUser = userService.getUserByParcours(parcours);
			modelAndView.addObject("Allmodule", modules);
			modelAndView.addObject("Allparcours", parcours);
		}	
		for(User userC: listUser)
			for(int year: years)
				if(year == Integer.parseInt(userC.getPromo()))
					years.add(year);
		modelAndView.addObject("Alluser", listUser);
		modelAndView.addObject("years", years);

	}
}
