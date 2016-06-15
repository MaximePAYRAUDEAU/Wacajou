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

	@RequestMapping(value = "/user/add")
	public ModelAndView addUserPage(ModelAndView modelAndView) {
		modelAndView.setViewName("admin/user/create");
		Statut[] statut = Statut.values();
		List<Statut> canCreate = new ArrayList<Statut>();
		for (Statut stat : statut)
			if (stat.canCreate())
				canCreate.add(stat);
		Calendar cal = Calendar.getInstance();
		int year = cal.get(cal.YEAR);
		List<String> promo = new ArrayList<String>();
		promo.add("NONE");
		for (int i = 0; i < 5; i++) {
			int value = year + i;
			promo.add("" + value);
		}
		modelAndView.addObject("promos", promo);
		modelAndView.addObject("statuts", canCreate);
		return modelAndView;
	}

	/**
	 * <h1>Add new user to our database that allow him to connect
	 * 
	 * @param login
	 * @param promo
	 * @param statut
	 * @Param parcours
	 * @param modelAndView
	 * @return modelAndView
	 */
	@RequestMapping(value = "user/add/process", method = RequestMethod.POST)
	public ModelAndView addingUser(@RequestParam("login") String login,
			@RequestParam("promo") String promo,
			@RequestParam("statut") Statut statut,
			@RequestParam("parcours") Long parcours_id,
			@RequestParam(value = "file", required = false) MultipartFile file,
			@ModelAttribute("userRight") Statut right,
			ModelAndView modelAndView) {
		System.out.println("Parcours : " + parcours_id);
		if (right.equals(Statut.RESPO_PARCOURS)
				|| right.equals(Statut.RESPO_PEDAGOGIQUE)
				|| right.equals(Statut.RESPO_PEDAGOGIQUE)) {

			if ((!file.isEmpty()) && (file != null)) {
				String filename = file.getOriginalFilename();
				String[] tmpFile = filename.split("\\.");
				String extension = tmpFile[tmpFile.length - 1].toLowerCase();
				boolean upload = false;
				System.out.println("Extension : " + extension);
				if (extension.equals("csv") || extension.equals("xls")
						|| extension.equals("xlsx"))
					upload = true;
				if (upload) {
					if (file.getSize() > 0) { // writing file to a directory
						String filepath = servletContext
								.getAttribute(WebServletConfig.ROOT_PATH)
								+ "/upload/" + file.getOriginalFilename();
						System.out.println(filepath);
						File upLoadedfile = new File(filepath);
						try {
							file.transferTo(upLoadedfile);
							if (extension.equals("xlsx")
									|| extension.equals("xls")) {
								XLSXFile myFile = new XLSXFile(upLoadedfile);
								List<String> userFile = myFile.Read();
								for (String user : userFile)
									userService
											.Create(user,
													promo,
													statut,
													parcoursService
															.getOne(parcours_id));
							} else if (extension.equals("csv")) {
								CSVFile obj = new CSVFile(upLoadedfile);
								HashMap<Integer, String[]> maps = obj.run();
								for (Entry<Integer, String[]> entry : maps
										.entrySet())
									userService
											.Create(entry.getValue()[0],
													promo,
													statut,
													parcoursService
															.getOne(parcours_id));
							}
						} catch (IllegalStateException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}

					}
				}
			} else if (login != null)
				userService.Create(login, promo, statut,
						parcoursService.getOne(parcours_id));
			if (userService.getError() != null)
				modelAndView.addObject("success",
						"User(s) was succesfully added.");
			else
				modelAndView.addObject("error", userService.getError());
			modelAndView.setViewName("redirect:../../../admin");
		} else {
			modelAndView.setViewName("redirect:../../home");
		}
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
