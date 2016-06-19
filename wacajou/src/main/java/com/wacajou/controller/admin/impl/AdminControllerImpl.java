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
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wacajou.common.file.Upload;
import com.wacajou.common.file.csv.CSVFile;
import com.wacajou.common.file.xls.XLSXFile;
import com.wacajou.config.WebServletConfig;
import com.wacajou.controller.admin.AdminModuleController;
import com.wacajou.controller.admin.AdminParcoursController;
import com.wacajou.controller.admin.AdminUserController;
import com.wacajou.controller.common.GenericModelAttribute;
import com.wacajou.controller.form.ModuleForm;
import com.wacajou.controller.form.ParcoursForm;
import com.wacajou.data.jpa.domain.Domain;
import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.Parcours;
import com.wacajou.data.jpa.domain.Status;
import com.wacajou.data.jpa.domain.Statut;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.service.ModuleService;
import com.wacajou.data.jpa.service.ParcoursService;
import com.wacajou.data.jpa.service.UserService;

@Controller
@RequestMapping("/administration")
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
	public ModelAndView createModule(
			@ModelAttribute("userRight")Statut right,
			@ModelAttribute(AdminModelAttribute.LIST_RESPO_MODULE) List<User> respoModule, 
			@ModelAttribute(AdminModelAttribute.LIST_RESPO_PARCOURS) List<User> respoParcours,
			ModuleForm moduleForm,
			ModelAndView modelAndView) {
		modelAndView.setViewName("admin/module/create");
		List<User> listReturn = respoModule;
		for(User user: respoParcours)
			listReturn.add(user);
		modelAndView.addObject("users", listReturn);
		return modelAndView;
	}
	
	@Override
	public @ResponseBody ModelAndView createModuleSendTest(@ModelAttribute("userRight")Statut right, @Valid ModuleForm moduleForm, BindingResult bindingResult,
			ModelAndView modelAndView) {
		if(right.equals(Statut.RESPO_PEDAGOGIQUE)){
			if(bindingResult.hasErrors()){
				System.out.println(bindingResult.toString());
				return modelAndView;
			}else{
				Module module = moduleForm.getModule();
				MultipartFile image = moduleForm.getImage();
				if(image != null && !image.isEmpty()){
					String name = moduleForm.getName();
					Upload upload = new Upload();
					String img = upload.upload(image, name, autoFormat, servletContext);
					if(upload.getError() != null)
						modelAndView.addObject("error", upload.getError());	
					else 
						module.setImage(img);
				}
				if(moduleForm.getRespo() != 0){
					User user = userService.getUser(moduleForm.getRespo());
					module.setRespo(user);
				}
				moduleService.Create(module);
			}
			modelAndView.setViewName("redirect:../../../administration");
		}else
			modelAndView.setViewName("forward:../../home");
		return modelAndView;
	}
	
	@Override
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
	public ModelAndView editModule(
			@ModelAttribute("userRight")Statut right, 
			@ModelAttribute(AdminModelAttribute.LIST_RESPO_MODULE) List<User> respoModule, 
			@ModelAttribute(AdminModelAttribute.LIST_RESPO_PARCOURS) List<User> respoParcours,
			@RequestParam Long id, 
			ModuleForm moduleForm,
			ModelAndView modelAndView) {
		Module module = moduleService.getOne(id);
		List<User> listReturn = respoModule;
		for(User user: respoParcours)
			listReturn.add(user);
		moduleForm.setModule(module);
		modelAndView.addObject(moduleForm);
		modelAndView.addObject("users", listReturn);
		modelAndView.setViewName("admin/module/edit");
		return modelAndView;
	}

	@Override
	public @ResponseBody ModelAndView editModuleProcess(@ModelAttribute("userRight")Statut right,  @ModelAttribute @Valid ModuleForm moduleForm, BindingResult bindingResult,
			ModelAndView modelAndView) {
		if(right.equals(Statut.RESPO_PEDAGOGIQUE) || right.equals(Statut.RESPO_MODULE) || right.equals(Statut.ADMIN)){
			if(bindingResult.hasErrors()){
				System.out.println(bindingResult.toString());
			}else{
				MultipartFile image = moduleForm.getImage();
				Module module = moduleService.getByName(moduleForm.getName());
				if(image != null && !image.isEmpty()){
					String name = moduleForm.getName();
					Upload upload = new Upload();
					String img = upload.upload(image, name, autoFormat, servletContext);
					if(upload.getError() != null)
						modelAndView.addObject("error", upload.getError());	
					else
						module.setImage(img);
				}
				if(moduleForm.getRespo() != 0){
					User user = userService.getUser(moduleForm.getRespo());
					module.setRespo(user);
				}else
					module.setRespo(null);
				module = moduleForm.save(module);
				System.out.println(module.getProject());
				System.out.println(moduleForm.getProject());
				moduleService.Update(module);
			}
			modelAndView.setViewName("redirect:../../../administration");
		}else{
			System.out.println("Error");
			modelAndView.setViewName("redirect:../../../home");
		}
		return modelAndView;
	}
	
	@Override
	public ModelAndView createParcours(ModelAndView modelAndView) {
		modelAndView.setViewName("admin/parcours/create");
		return modelAndView;
	}

	@Override
	public @ResponseBody ModelAndView createParcoursSend(@RequestParam String name,
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
		if(!description.isEmpty())
			map.put("description", description);
		if(!full_file_name.equals(null))
			map.put("image", full_file_name);
		if(!domain.isEmpty())
			map.put("domain", domain);
		if(!module.isEmpty())
			map.put("module", module);
		if(!module_optional.isEmpty())
			map.put("module_optional", module_optional);
		if(user_id != 0)
			map.put("user", userService.getUser(user_id));
		parcoursService.Create(map);
		if (parcoursService.getError() != null)
			modelAndView.addObject("error", parcoursService.getError());
		else
			modelAndView.addObject("success",
					"Le module à été créer avec succès");
		modelAndView.setViewName("forward:../../../administration");
		return modelAndView;
	}

	@Override
	public @ResponseBody ModelAndView editParcours(
			@ModelAttribute(AdminModelAttribute.LIST_RESPO_PARCOURS) List<User> respoParcours,
			@ModelAttribute(AdminModelAttribute.ALL_MODULE) List<Module> listModule,
			@RequestParam Long id, 
			ParcoursForm parcoursForm,
			ModelAndView modelAndView) {
		Parcours parcours = parcoursService.getOne(id);
		List<Module> module = parcoursService.getModulesPrincipaux(parcours);
		List<Module> module_optional = parcoursService.getModulesOptional(parcours);
		List<Module> all_module_without_module = new ArrayList<Module>();
		List<Module> all_module_without_module_optional = new ArrayList<Module>();
		for(Module tmp: listModule){
			all_module_without_module.add(tmp);
			all_module_without_module_optional.add(tmp);
		}
		for(Module iModule: module)
			all_module_without_module.remove(iModule);
		
		for(Module eModule: module_optional)
			all_module_without_module_optional.remove(eModule);
		
		modelAndView.addObject("module_selected", module);
		modelAndView.addObject("module_selected_optional", module_optional);
		modelAndView.addObject("module_non_selected", all_module_without_module);
		modelAndView.addObject("module_non_selected_optional", all_module_without_module_optional);

		parcoursForm.setParcours(parcours);
		modelAndView.addObject("parcours", parcours);
		modelAndView.setViewName("admin/parcours/edit");
		return modelAndView;
	}

	@Override
	public ModelAndView editParcoursProcess(
			@RequestParam String name,
			@RequestParam String domain,
			@RequestParam(required = false) String description,
			@RequestParam(required = false) MultipartFile image,
			@RequestParam(required = false) long user_id,
			@RequestParam(required = false) List<Long> module,
			@RequestParam(required = false) List<Long> module_optional,
			RedirectAttributes redirectAttributes, 
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
					full_file_name = "parcours_" + name + "." + extension;
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(new File(
									servletContext.getAttribute(WebServletConfig.ROOT_PATH) + "/parcours/"
											+ full_file_name)));
					FileCopyUtils.copy(image.getInputStream(), stream);
					stream.close();
					redirectAttributes.addFlashAttribute("message",
							"You successfully uploaded " + name + "!");
				} catch (Exception e) {	}				
		} 
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("description", description);
		map.put("image", full_file_name);
		map.put("domain", domain);
		map.put("module", module);
		map.put("module_optional", module_optional);
		if(user_id != 0)
			map.put("user",  userService.getUser(user_id));
		else
			map.put("user",null);
		parcoursService.Update(parcoursService.getByName(name), map);
		if (parcoursService.getError() != null)
			modelAndView.addObject("error", parcoursService.getError());
		else
			modelAndView.addObject("success",
					"Le module à été créer avec succès");
		modelAndView.setViewName("forward:../../../administration");
		return modelAndView;
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
