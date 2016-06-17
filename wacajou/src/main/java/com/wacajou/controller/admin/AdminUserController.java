package com.wacajou.controller.admin;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wacajou.data.jpa.domain.Statut;

public interface AdminUserController {
	
	@RequestMapping(value = "user/create")
	public ModelAndView createUser(ModelAndView modelAndView);
	
	@RequestMapping(value = "user/create/process", method = RequestMethod.POST)
	public ModelAndView addingUser(
			@RequestParam("login") String login,
			@RequestParam("promo") String promo,
			@RequestParam("statut") Statut statut,
			@RequestParam("parcours") Long parcours_id,
			@RequestParam(value = "file", required = false) MultipartFile file,
			@ModelAttribute("userRight") Statut right,
			ModelAndView modelAndView);
}
