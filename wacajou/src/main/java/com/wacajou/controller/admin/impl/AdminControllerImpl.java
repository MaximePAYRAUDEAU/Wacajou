package com.wacajou.controller.admin.impl;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wacajou.config.WebServletConfig;
import com.wacajou.controller.admin.AdminModuleController;
import com.wacajou.controller.admin.AdminParcoursController;
import com.wacajou.controller.admin.AdminUserController;
import com.wacajou.data.jpa.domain.Domain;
import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.Statut;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.service.ModuleService;
import com.wacajou.data.jpa.service.ParcoursService;
import com.wacajou.data.jpa.service.UserService;
import com.wacajou.file.csv.CSVFile;
import com.wacajou.file.xls.XLSXFile;

@Controller
public class AdminControllerImpl extends AdminModelAttribute implements AdminModuleController, AdminParcoursController, AdminUserController{
	private String[] autoFormat = {"png", "jpeg", "jpg"};

	@Autowired
	private UserService userService;
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private ParcoursService parcoursService;
	@Autowired
	private ServletContext servletContext;
	
	@Override
	public ModelAndView createModule(@ModelAttribute("Alluser") List<User> listUsers, ModelAndView modelAndView) {
		modelAndView.setViewName("admin/module/create");
		List<User> listReturn = new ArrayList<User>();
		for(int i = 0; i < listUsers.size(); i++){
			User user = listUsers.get(i);
			if(user.getStatut().equals(Statut.RESPO_MODULE))
				listReturn.add(user);
		}
		modelAndView.addObject("users", listReturn);
		
		return modelAndView;
	}

	@Override
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
			ModelAndView modelAndView) {
		String message = "";
		String error = null;
		String full_file_name = null;

		if ((!image.isEmpty()) && (image != null)) {
			String filename = null;
			filename = image.getOriginalFilename();
			String[] tmpFile = filename.split("\\.");
			String extension = tmpFile[tmpFile.length - 1].toLowerCase();
			boolean upload = false;
			for (int i = 0; i < autoFormat.length; i++)
				if (extension.equals(autoFormat[i]))
					upload = true;
			if (upload)
				try {
					full_file_name = "module_" + name + "." + extension;
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(new File(
									servletContext.getAttribute(WebServletConfig.ROOT_PATH) + "/module/"
											+ full_file_name)));
					FileCopyUtils.copy(image.getInputStream(), stream);
					stream.close();
					message = "You successfully uploaded " + name + "!";
				} catch (Exception e) {
					error = "You failed to upload " + name + " => " + e.getMessage();
				}
			else
				error = "You failed to upload " + name + " because the file extension was not supported.";
		} else
			error = "You failed to upload " + name + " because the file was empty";
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("name", name);
		map.put("description", description);
		map.put("image", full_file_name);
		map.put("domain", domain);
		map.put("user", userService.getUser(user_id));
		map.put("code", code);
		map.put("semester", semester);
		map.put("tp_cours", tp_cours);
		map.put("project", project);
		map.put("ects", ects);
		map.put("link", link);
		if (error != null) {
			moduleService.Create(map);
			if (moduleService.getError() != null)
				modelAndView.addObject("error", moduleService.getError());
			else
				modelAndView.addObject("success", "Le module à été créer avec succès");
		} else
			modelAndView.addObject("error", error);
		modelAndView.setViewName("forward:../../administration");
		return modelAndView;
	}
	
	@Override
	public ModelAndView editModule(@RequestParam Long id, ModelAndView modelAndView) {
		Module module = moduleService.getOne(id);
		List<User> listReturn = new ArrayList<User>();
		List<User> listUsers = userService.getAllUser();
		for(int i = 0; i < listUsers.size(); i++){
			User user = listUsers.get(i);
			if(user.getStatut().equals(Statut.RESPO_MODULE))
				listReturn.add(user);
		}
		modelAndView.addObject("users", listReturn);
		modelAndView.addObject("module", module);		
		modelAndView.setViewName("edit/module");
		return modelAndView;
	}

	@Override
	public ModelAndView editModuleProcess(@ModelAttribute Module module, 			
			@RequestParam(required = false) MultipartFile image,			
			ModelAndView modelAndView) {
		String full_file_name = null;

		if ((!image.isEmpty()) && (image != null)) {
			String filename = null;
			filename = image.getOriginalFilename();
			String[] tmpFile = filename.split("\\.");
			String extension = tmpFile[tmpFile.length - 1].toLowerCase();
			boolean upload = false;
			for (int i = 0; i < autoFormat.length; i++)
				if (extension.equals(autoFormat[i]))
					upload = true;
			if (upload)
				try {
					full_file_name = "module_" + module.getName() + "." + extension;
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(new File(
									servletContext.getAttribute(WebServletConfig.ROOT_PATH) + "/module/"
											+ full_file_name)));
					FileCopyUtils.copy(image.getInputStream(), stream);
					stream.close();
					module.setImage(full_file_name);
				} catch (Exception e) {}
		}
		moduleService.Update(module);
		modelAndView.setViewName("redirect:../../administration");
		return modelAndView;
	}
	
	@Override
	public ModelAndView createParcours(ModelAndView modelAndView) {
		modelAndView.setViewName("admin/parcours/create");
		List<User> listReturn = new ArrayList<User>();
		List<User> listUsers = userService.getAllUser(); // TO MODIFIE
		for (int i = 0; i < listUsers.size(); i++) {
			User user = listUsers.get(i);
			if (user.getStatut().equals(Statut.RESPO_PARCOURS))
				listReturn.add(user);
		}
		modelAndView.addObject("modules", moduleService.getAll());
		modelAndView.addObject("domains", Domain.values());
		modelAndView.addObject("users", listReturn);
		return modelAndView;
	}

	@Override
	public ModelAndView createParcoursSend(@RequestParam String name,
			@RequestParam String domain,
			@RequestParam(required = false) String description,
			@RequestParam(required = false) MultipartFile image,
			@RequestParam(required = false) long user_id,
			@RequestParam(required = false) List<Long> module,
			@RequestParam(required = false) List<Long> module_optional,
			RedirectAttributes redirectAttributes, ModelAndView modelAndView) {
		String full_file_name = null;

		if ((!image.isEmpty()) && (image != null)) {
			String filename = null;
			filename = image.getOriginalFilename();
			String[] tmpFile = filename.split("\\.");
			String extension = tmpFile[tmpFile.length - 1].toLowerCase();
			boolean upload = false;
			for (int i = 0; i < autoFormat.length; i++)
				if (extension.equals(autoFormat[i]))
					upload = true;
			if (upload)
				try {
					full_file_name = "parcours_" + name + "." + extension;
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(new File(
									servletContext.getAttribute(WebServletConfig.ROOT_PATH) + "/parcours/"
											+ full_file_name)));
					FileCopyUtils.copy(image.getInputStream(), stream);
					stream.close();
					redirectAttributes.addFlashAttribute("message",
							"You successfully uploaded " + name + "!");
				} catch (Exception e) {
					redirectAttributes.addFlashAttribute(
							"message",
							"You failed to upload " + name + " => "
									+ e.getMessage());
				}
			else
				redirectAttributes.addFlashAttribute("message",
						"You failed to upload " + name
								+ " because the file was not supported");
		} else
			redirectAttributes.addFlashAttribute("message",
					"You failed to upload " + name
							+ " because the file was empty");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("description", description);
		map.put("image", full_file_name);
		map.put("domain", domain);
		map.put("module", module);
		map.put("module_optional", module_optional);
		map.put("user",  userService.getUser(user_id));
		parcoursService.Create(map);
		if (parcoursService.getError() != null)
			modelAndView.addObject("error", parcoursService.getError());
		else
			modelAndView.addObject("success",
					"Le module à été créer avec succès");
		modelAndView.setViewName("forward:../../administration");
		return modelAndView;
	}

	@Override
	public ModelAndView editParcours(@RequestParam Long id, ModelAndView modelAndView) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelAndView editParcoursProcess(@ModelAttribute Module module, 			
			@RequestParam(required = false) MultipartFile image, ModelAndView modelAndView) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelAndView createUser(ModelAndView modelAndView) {
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
		modelAndView.setViewName("admin/user/create");
		return modelAndView;
	}

	@Override
	public ModelAndView addingUser(@RequestParam("login") String login,
			@RequestParam("promo") String promo,
			@RequestParam("statut") Statut statut,
			@RequestParam("parcours") Long parcours_id,
			@RequestParam(value = "file", required = false) MultipartFile file,
			@ModelAttribute("userRight") Statut right,
			ModelAndView modelAndView) {
		if (right.equals(Statut.RESPO_PARCOURS)
				|| right.equals(Statut.RESPO_PEDAGOGIQUE)
				|| right.equals(Statut.RESPO_PEDAGOGIQUE)) {
			if ((!file.isEmpty()) && (file != null)) {
				String filename = file.getOriginalFilename();
				String[] tmpFile = filename.split("\\.");
				String extension = tmpFile[tmpFile.length - 1].toLowerCase();
				boolean upload = false;
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
			modelAndView.setViewName("redirect:../../admin");
		} else {
			modelAndView.setViewName("redirect:../../home");
		}
		return modelAndView;
	}

	

	

}
