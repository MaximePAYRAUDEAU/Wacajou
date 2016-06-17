package com.wacajou.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wacajou.data.jpa.domain.Module;

public interface AdminParcoursController {
	@RequestMapping(value = "/parcours/create", method = RequestMethod.GET)
	public ModelAndView createParcours(ModelAndView modelAndView);
	
	@RequestMapping(value = "/parcours/create/process", method = RequestMethod.POST)
	public ModelAndView createParcoursSend(
			@RequestParam String name,
			@RequestParam String domain,
			@RequestParam(required = false) String description,
			@RequestParam(required = false) MultipartFile image,
			@RequestParam(required = false) long user_id,
			@RequestParam(required = false) List<Long> module,
			@RequestParam(required = false) List<Long> module_optional,
			RedirectAttributes redirectAttributes, ModelAndView modelAndView);
	
	@RequestMapping(value = "/parcours/edit")
	public ModelAndView editParcours(@RequestParam Long id, ModelAndView modelAndView);
	
	@RequestMapping(value = "/parcours/edit/process", method = RequestMethod.POST)
	public ModelAndView editParcoursProcess(
			@ModelAttribute Module module, 			
			@RequestParam(required = false) MultipartFile image,			
			ModelAndView modelAndView);
}
