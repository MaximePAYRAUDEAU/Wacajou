package com.wacajou.controller.admin;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.Statut;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.controller.common.*;
import com.wacajou.controller.form.ModuleForm;

public interface AdminModuleController {
	
	@RequestMapping( value = "/module/create", method = RequestMethod.GET)
	ModelAndView createModule(
			@ModelAttribute("userRight")Statut right,
			@ModelAttribute(GenericModelAttribute.RESPO_MODULE) List<User> respoModule, 
			@ModelAttribute(GenericModelAttribute.RESPO_PARCOURS) List<User> respoParcours,
			ModuleForm moduleForm,
			ModelAndView modelAndView);
	
	@RequestMapping(value = "/module/create/process", method = RequestMethod.POST)
	public @ResponseBody ModelAndView createModuleSendTest(
			@ModelAttribute("userRight")Statut right, 
			@Valid ModuleForm moduleForm, 
			BindingResult bindingResult, 
			ModelAndView modelAndView);
	
	//@RequestMapping(value = "/module/create/process", method = RequestMethod.POST)
	public @ResponseBody ModelAndView createModuleSend(@RequestParam String name, 
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
	
	@RequestMapping(value = "/module/edit")
	public ModelAndView editModule(
			@ModelAttribute("userRight")Statut right, 
			@ModelAttribute(GenericModelAttribute.RESPO_MODULE) List<User> respoModule, 
			@ModelAttribute(GenericModelAttribute.RESPO_PARCOURS) List<User> respoParcours,
			@RequestParam Long id, 
			ModuleForm moduleForm,
			ModelAndView modelAndView);
	
	@RequestMapping(value = "/module/edit/process", method = RequestMethod.POST)
	public @ResponseBody ModelAndView editModuleProcess(
			@ModelAttribute("userRight")Statut right, 
			@Valid ModuleForm moduleForm, 
			BindingResult bindingResult, 
			ModelAndView modelAndView);
	

}
