package com.wacajou.module.dev.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wacajou.config.WebServletConfig;
import com.wacajou.controller.common.GenericModelAttribute;
import com.wacajou.data.jpa.domain.Statut;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.service.ParcoursService;
import com.wacajou.data.jpa.service.UserService;
import com.wacajou.file.csv.CSVFile;
import com.wacajou.file.xls.XLSXFile;

@Controller
// @SessionAttributes(value = UserController.SESSION_USER, types = { User.class
// })
public class UserController extends GenericModelAttribute {

	private String[] autoFormat = { "pdf", "png", "jpeg" };
	@Autowired
	private UserService userService;
	@Autowired
	private ParcoursService parcoursService;
	@Autowired
	private ServletContext servletContext;

	@RequestMapping(value = { "user/{file_name}/add", "user/add/process" }, method = RequestMethod.GET)
	public ModelAndView addFileGet(ModelAndView modelAndView) {
		modelAndView.setViewName("user/profil");
		return modelAndView;
	}
	
	@RequestMapping(value = "user/{file_name}/add", method = RequestMethod.POST)
	public ModelAndView addFile(
			@PathVariable String file_name,
			@RequestParam(value = "image", required = false) MultipartFile image,
			@RequestParam(value = "ldm", required = false) MultipartFile ldm,
			@RequestParam(value = "cv", required = false) MultipartFile cv,
			@RequestParam(value = "notes", required = false) MultipartFile notes,
			@ModelAttribute("user") User user,
			RedirectAttributes redirectAttributes, 
			ModelAndView modelAndView) {
		MultipartFile file = null;

		if ((user != null) && user.isConnect()) {
			if (image != null)
				file = image;
			else if (ldm != null)
				file = ldm;
			else if (cv != null)
				file = cv;
			else if (notes != null)
				file = notes;

			if ((!file.isEmpty()) && (file != null) && (user != null)) {
				String filename = null;
				String full_file_name = null;
				filename = file.getOriginalFilename();
				String[] tmpFile = filename.split("\\.");
				String extension = tmpFile[tmpFile.length - 1].toLowerCase();
				boolean upload = false;
				for (int i = 0; i < autoFormat.length; i++)
					if (extension.equals(autoFormat[i]))
						upload = true;
				if (upload)
					try {
						full_file_name = file_name + "-" + user.getLogin()
								+ "." + extension;
						BufferedOutputStream stream = new BufferedOutputStream(
								new FileOutputStream(
										new File(
												servletContext
														.getAttribute(WebServletConfig.ROOT_PATH)
														+ "/user/"
														+ full_file_name)));
						FileCopyUtils.copy(file.getInputStream(), stream);
						stream.close();
						redirectAttributes.addFlashAttribute("message",
								"You successfully uploaded " + file_name + "!");
						userService.updateInfo(user, full_file_name, file_name);
						if (userService.getError() != null)
							redirectAttributes.addFlashAttribute("message",
									"You failed to upload " + file_name);
					} catch (Exception e) {
						redirectAttributes.addFlashAttribute("message",
								"You failed to upload " + file_name + " => "
										+ e.getMessage());
					}
				else
					redirectAttributes.addFlashAttribute("message",
							"You failed to upload " + file_name
									+ " because the file was not supported");
			} else
				redirectAttributes.addFlashAttribute("message",
						"You failed to upload " + file_name
								+ " because the file was empty");

			modelAndView.setViewName("redirect:../../profil");
		} else {
			modelAndView.setViewName("redirect:../../home");
		}
		return modelAndView;
	}
}
