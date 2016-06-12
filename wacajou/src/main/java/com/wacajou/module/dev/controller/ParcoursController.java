package com.wacajou.module.dev.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wacajou.WacajouApplication;
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
	private String[] autoFormat = { "png", "jpeg" };

	@Autowired
	private UserService userService;

	@Autowired
	private ParcoursService parcoursService;

	@Autowired
	private ModuleService moduleService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createParcours(ModelAndView modelAndView) {
		modelAndView.setViewName("admin/parcours/create");
		List<User> listReturn = new ArrayList<User>();
		List<User> listUsers = userService.getAllUser();
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

	@RequestMapping(value = "/create/send", method = RequestMethod.POST)
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
									WacajouApplication.ROOT + "/images/parcours/"
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
}