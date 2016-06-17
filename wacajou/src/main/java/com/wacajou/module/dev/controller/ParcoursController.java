package com.wacajou.module.dev.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wacajou.WacajouApplication;
import com.wacajou.config.WebServletConfig;
import com.wacajou.controller.common.GenericModelAttribute;
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
public class ParcoursController extends GenericModelAttribute{

	@Autowired
	private UserService userService;

	@Autowired
	private ParcoursService parcoursService;

	@Autowired
	private ModuleService moduleService;

	@RequestMapping(value = "/consult")
	public ModelAndView consultParcours(@RequestParam String name) {
		ModelAndView modelAndView = new ModelAndView();
		Parcours parcours = parcoursService.getByName(name);

		if (parcours != null) {
			modelAndView.addObject("parcoursConsult", parcours);
			modelAndView.addObject("modulePrincipaux",
					parcoursService.getModulesPrincipaux(parcours));
			modelAndView.addObject("moduleOptional",
					parcoursService.getModulesOptional(parcours));
			modelAndView.setViewName("consult/parcours");
		} else
			modelAndView.setViewName("redirect:../../home");
		return modelAndView;
	}
	
	@RequestMapping(value = "/inscription")
	public ModelAndView inscriptionParcours(@ModelAttribute("user") User user, ModelAndView modelAndView){
		if(user.isConnect()){
			modelAndView.setViewName("inscription/parcours");
		}else{
			modelAndView.setViewName("redirect:../../home");
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/inscription/process", method = RequestMethod.POST)
	public ModelAndView inscriptionParcoursProcess(@ModelAttribute("user") User user, @RequestParam("parcours") Long parcours_id, ModelAndView modelAndView){
		if(user.isConnect()){
			Parcours parcours = parcoursService.getOne(parcours_id);
			userService.setUserParcours(user, parcours);
			modelAndView.addObject("userParcours", userService.getUserParcours(user));
			modelAndView.setViewName("forward:../../module/inscription");
		}else{
			modelAndView.setViewName("redirect:../../home");
		}
		return modelAndView;
	}
}