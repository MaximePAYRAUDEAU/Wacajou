package com.wacajou.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.User;

public interface AdminModuleController {
	
	@RequestMapping(value = "/module/create", method = RequestMethod.GET)
	public ModelAndView createModule(@ModelAttribute("Alluser") List<User> listUsers, ModelAndView modelAndView);
	
	@RequestMapping(value = "/module/create/process", method = RequestMethod.POST)
	public ModelAndView createModuleSend(@RequestParam String name, 
			@RequestParam String domain,
			@RequestParam(required = false) String description,
			@RequestParam(required = true) int code,
			@RequestParam(required = false) String semester,
			@RequestParam(required = false) int tp_cours,
			@RequestParam(required = false) int project,
			@RequestParam(required = true) double ects,
			@RequestParam(required = false) String link,
			@RequestParam(required = false) MultipartFile image,
			@RequestParam(required = false) long user_id,
			ModelAndView modelAndView);
	
	@RequestMapping(value = "/edit")
	public ModelAndView editModule(@RequestParam Long id, ModelAndView modelAndView);
	
	@RequestMapping(value = "/edit/process", method = RequestMethod.POST)
	public ModelAndView editModuleProcess(@ModelAttribute Module module, 			
			@RequestParam(required = false) MultipartFile image,			
			ModelAndView modelAndView);

}
