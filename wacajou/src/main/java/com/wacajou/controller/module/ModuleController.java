package com.wacajou.controller.module;

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

import com.wacajou.config.WebServletConfig;
import com.wacajou.controller.common.GenericModelAttribute;
import com.wacajou.data.jpa.domain.Comments;
import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.Parcours;
import com.wacajou.data.jpa.domain.Rating;
import com.wacajou.data.jpa.domain.Statut;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.service.ModuleService;
import com.wacajou.data.jpa.service.UserService;

@Controller
@RequestMapping("/module")
public class ModuleController extends GenericModelAttribute{

	@Autowired 
	private UserService userService;
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private ServletContext servletContext;
	
	@RequestMapping(value = "/consult")
	public ModelAndView consultModule(@RequestParam String name){
		ModelAndView modelAndView = new ModelAndView();
		Module module = moduleService.getByName(name);
		if( module != null ){
			try{
				List<Comments> comments = moduleService.getComments(module);
				modelAndView.addObject("comments", comments);
				modelAndView.addObject("commentAverage", moduleService.getAverage(module, comments));
			}catch(Exception e){
				e.printStackTrace();
			}
			modelAndView.addObject("moduleConsult", module);
			modelAndView.setViewName("consult/module");
		}else
			modelAndView.setViewName("redirect:../../home");
		return modelAndView;		
	}
		
	@RequestMapping(value = "/inscription", method = RequestMethod.POST)
	public ModelAndView inscriptionModule(@ModelAttribute("user") User user, @RequestParam("parcours") Parcours parcours, ModelAndView modelAndView){
		if(user.isConnect()){
			List<Module> modules = moduleService.getByParcours(parcours);
			List<Module> modules_optional = moduleService.getByParcoursOptional(parcours);
			modelAndView.addObject("modules", modules);
			modelAndView.addObject("modules_optional", modules_optional);
			modelAndView.addObject("parcours", parcours);
			modelAndView.setViewName("inscription/module");
		}else{
			modelAndView.setViewName("redirect:../../home");
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/inscription/process", method = RequestMethod.POST)
	public ModelAndView inscriptionModuleProcess(@ModelAttribute("userParcours") Parcours parcours, @ModelAttribute("user") User user, @RequestParam("modules") List<Long> modules, ModelAndView modelAndView){
		if(user.isConnect()){
			List<Module> module = new ArrayList<Module>();
			for(Long id: modules)
				module.add(moduleService.getOne(id));
			userService.setUserModule(user, parcours, module);
			modelAndView.addObject("userModule", userService.getUserModule(user));
			modelAndView.setViewName("user/profil");
		}else{
			modelAndView.setViewName("redirect:../../../home");
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public ModelAndView setComment(@ModelAttribute("user") User user, 
			@RequestParam("name") String name, 
			@RequestParam("title") String title, 
			@RequestParam("message") String message, 
			@RequestParam("rating") Rating rating, 
			ModelAndView modelAndView){
		if(user.isConnect()){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("tilte", title);
			map.put("message", message);
			map.put("rating", rating);
			Module module = moduleService.getByName(name);
			moduleService.Evaluate(module, user, map);
			modelAndView.addObject("moduleConsult", module);
			modelAndView.setViewName("forward:../../module/consult");
		}else
			modelAndView.setViewName("redirect:../../home");
		return modelAndView;		
	}
} 
