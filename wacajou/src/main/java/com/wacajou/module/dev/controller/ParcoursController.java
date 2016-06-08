package com.wacajou.module.dev.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wacajou.data.jpa.domain.Domain;
import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.Parcours;
import com.wacajou.data.jpa.domain.Statut;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.service.ModuleService;
import com.wacajou.data.jpa.service.ParcoursService;
import com.wacajou.data.jpa.service.UserService;

@Controller
@RequestMapping("/parcours")
public class ParcoursController {
	
	@Autowired 
	private UserService userService;
	
	@Autowired
	private ParcoursService parcoursService;
	
	@Autowired
	private ModuleService moduleService;
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createParcours( ModelAndView modelAndView){
		modelAndView.setViewName("admin/parcours/create");
		List<User> listReturn = new ArrayList<User>();
		List<User> listUsers = userService.getAllUser();
		for(int i = 0; i < listUsers.size(); i++){
			User user = listUsers.get(i);
			if(user.getStatut().equals(Statut.RESPO_PARCOURS))
				listReturn.add(user);	
		}
		modelAndView.addObject("modules", moduleService.getAllModule());
		modelAndView.addObject("domains", Domain.values());
		modelAndView.addObject("users", listReturn);
		return modelAndView;
	}
	
	@RequestMapping(value = "/create/send", method = RequestMethod.POST)
	public ModelAndView createParcoursSend(@RequestParam String name, 
			@RequestParam String domain,
			@RequestParam(required = false) String description,
			@RequestParam(required = false) MultipartFile image,
			@RequestParam(required = false) long user_id,
			@RequestParam(required = false) List<Long> module,
			@RequestParam(required = false) List<Long> module_optional,
			ModelAndView modelAndView){
		parcoursService.Create(name, description, null, domain, module, module_optional ,userService.getUser(user_id));
		if(parcoursService.getError() != null )
			modelAndView.addObject("error", parcoursService.getError());
		else
			modelAndView.addObject("success", "Le module à été créer avec succès");
		modelAndView.setViewName("forward:../../administration");
		return modelAndView;	
	}
	
	@RequestMapping(value = "/consult")
	public ModelAndView consultParcours(@RequestParam String name){
		ModelAndView modelAndView = new ModelAndView();
		Parcours parcours = parcoursService.Consult(name);
		
		if( parcours != null ){
			modelAndView.addObject("parcoursConsult", parcours);
			modelAndView.addObject("modulePrincipaux",  parcoursService.getModulesPrincipaux(parcours));
			modelAndView.addObject("moduleOptional",  parcoursService.getModulesOptional(parcours));
			modelAndView.setViewName("consult/parcours");
		}else
			modelAndView.setViewName("redirect:../../home");
		return modelAndView;		
	}
}