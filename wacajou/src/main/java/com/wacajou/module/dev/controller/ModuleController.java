package com.wacajou.module.dev.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wacajou.data.jpa.domain.Domain;
import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.Statut;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.service.ModuleService;
import com.wacajou.data.jpa.service.UserService;

@Controller
@RequestMapping("/module")
public class ModuleController {
	
	@Autowired 
	private UserService userService;
	
	@Autowired
	private ModuleService moduleService;
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createModule(HttpSession session){
		ModelAndView modelAndView = new ModelAndView("admin/module/create");
		List<User> listReturn = new ArrayList<User>();
		List<User> listUsers = userService.getAllUser();
		for(int i = 0; i < listUsers.size(); i++){
			User user = listUsers.get(i);
			if(user.getStatut().equals(Statut.RESPO_MODULE)){
				listReturn.add(user);
			}
		}
		modelAndView.addObject("domains", Domain.values());
		modelAndView.addObject("users", listReturn);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/create/send", method = RequestMethod.POST)
	public ModelAndView createModuleSend(@RequestParam String name, 
			@RequestParam String domain,
			@RequestParam(required = false) String description,
			@RequestParam(required = false) MultipartFile image,
			@RequestParam(required = false) long user_id,
			HttpSession session){
		ModelAndView modelAndView = createModule(session);
		modelAndView.setViewName("admin/module/create");
		moduleService.Create(name, description, null, domain, userService.getUser(user_id));
		if(moduleService.getError() != null )
			modelAndView.addObject("error", moduleService.getError());
		else
			modelAndView.addObject("success", "Le module à été créer avec succès");
		return modelAndView;	
	}
}
