package com.wacajou.module.dev.controller;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wacajou.WacajouApplication;
import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.Parcours;
import com.wacajou.data.jpa.domain.Reason;
import com.wacajou.data.jpa.domain.Statut;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.domain.UserInfo;
import com.wacajou.data.jpa.service.UserService;

@Controller
@SessionAttributes(value = UserController.SESSION_USER, types = { User.class })
public class UserController {
	protected static final String SESSION_USER = "session_user";

	private String[] autoFormat = {"pdf", "png", "jpeg"};
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/user/add")
	public ModelAndView addUserPage() {
		ModelAndView mandv = new ModelAndView();
		mandv.setViewName("admin/user/create");
		Statut[] statut = Statut.values();
		mandv.addObject("statuts", statut);
		return mandv;
	}
	
	@RequestMapping(value = "/profil")
	public ModelAndView profilPage(HttpSession session, ModelAndView mandv) {
		mandv.setViewName("user/profil");
		User user = (User) session.getAttribute("session_user");
		
		Statut right = null;
		UserInfo userInfo = null;
		Parcours parcours = null;
		List<Module> modules = null;
		
		if(user != null){
	
			if(!(user.getStatut().equals(Statut.STUDENT) || user.getStatut().equals(Statut.ANCIEN)) )
				right = user.getStatut();
		
			userInfo = userService.getInfos(user);
			parcours = userService.getUserParcours(user);
			modules = userService.getUserModule(user);
		}
		
		mandv.addObject("user", user);
		mandv.addObject("userInfo", userInfo);
		mandv.addObject("right", right);
		mandv.addObject("parcours", parcours);
		mandv.addObject("modules", modules);
		mandv.addObject("reasons", Reason.values());
		
		return mandv;
	}

	/**
	 * <h1>Add new user to our database that allow him to connect
	 * 
	 * @param login
	 * @param promo
	 * @param statut
	 * @param modelAndView
	 * @return modelAndView
	 */
	@RequestMapping(value = "user/add/process", method = RequestMethod.POST)
	public ModelAndView addingUser(
			@RequestParam("login") String login, 
			@RequestParam("promo") String promo,
			@RequestParam("statut") String statut, 
			ModelAndView modelAndView) {
		userService.Create(login, promo, statut);
		modelAndView.setViewName("admin/admin");
		if(userService.getError() != null)
			modelAndView.addObject("success", "User was succesfully added.");
		else
			modelAndView.addObject("error", userService.getError());
		return modelAndView;
	}

	@RequestMapping(value = "user/{file_name}/add", method = RequestMethod.POST)
	public ModelAndView addFile(@PathVariable String file_name, 
			@RequestParam(value = "image", required = false) MultipartFile image, 
			@RequestParam(value = "ldm", required = false) MultipartFile ldm, 
			@RequestParam(value = "cv", required = false) MultipartFile cv, 
			@RequestParam(value = "notes", required = false) MultipartFile notes, 
			RedirectAttributes redirectAttributes, 
			HttpSession session,
			ModelAndView modelAndView){
		MultipartFile file = null;
		
		User user = (User) session.getAttribute(SESSION_USER);

		if(image != null)
			file = image;
		else if(ldm != null)
			file = ldm;
		else if(cv != null)
			file = cv;
		else if(notes != null)
			file = notes;
		
		if ((!file.isEmpty()) && (file != null) && (user != null)){
			String filename = null;
			String full_file_name = null;
			filename = file.getOriginalFilename();
			String[] tmpFile = filename.split("\\.");
			String extension = tmpFile[tmpFile.length-1].toLowerCase();
			boolean upload = false;
			for(int i = 0; i < autoFormat.length; i++)
				if(extension.equals(autoFormat[i]))
					upload = true;
			if(upload)
				try {
					full_file_name = file_name + "-" + user.getLogin() + "." + extension;
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(new File(WacajouApplication.ROOT + "/images/user/" + full_file_name)));
	                FileCopyUtils.copy(file.getInputStream(), stream);
					stream.close();
					redirectAttributes.addFlashAttribute("message",
							"You successfully uploaded " + file_name + "!");
					userService.updateInfo(user, full_file_name, file_name);
					if(userService.getError() != null)
						redirectAttributes.addFlashAttribute("message",
								"You failed to upload " + file_name);
				}
				catch (Exception e) {
					redirectAttributes.addFlashAttribute("message",
							"You failed to upload " + file_name + " => " + e.getMessage());
				}
			else
				redirectAttributes.addFlashAttribute("message",
						"You failed to upload " + file_name + " because the file was not supported");
		} else
			redirectAttributes.addFlashAttribute("message",
					"You failed to upload " + file_name + " because the file was empty");
		
		Statut right = null;
		UserInfo userInfo = null;
		Parcours parcours = null;
		List<Module> modules = null;
		
		if(user != null){
	
			if(!(user.getStatut().equals(Statut.STUDENT) || user.getStatut().equals(Statut.ANCIEN)) )
				right = user.getStatut();
		
			userInfo = userService.getInfos(user);
			parcours = userService.getUserParcours(user);
			modules = userService.getUserModule(user);
		}
		modelAndView.addObject("user", user);
		modelAndView.addObject("userInfo", userInfo);
		modelAndView.addObject("right", right);
		modelAndView.addObject("parcours", parcours);
		modelAndView.addObject("modules", modules);
		modelAndView.setViewName("user/profil");
		return modelAndView;		
	}
}
