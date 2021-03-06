package com.wacajou.module.dev.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wacajou.WacajouApplication;
import com.wacajou.controller.common.GenericModelAttribute;
import com.wacajou.data.jpa.domain.Comments;
import com.wacajou.data.jpa.domain.Domain;
import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.Parcours;
import com.wacajou.data.jpa.domain.Rating;
import com.wacajou.data.jpa.domain.Statut;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.service.CommentService;
import com.wacajou.data.jpa.service.ModuleService;
import com.wacajou.data.jpa.service.UserService;

@Controller
@RequestMapping("/module")
public class ModuleController extends GenericModelAttribute{
	private String[] autoFormat = {"png", "jpeg"};

	@Autowired 
	private UserService userService;
	
	@Autowired
	private ModuleService moduleService;
	
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createModule(HttpSession session, ModelAndView modelAndView){
		modelAndView.setViewName("admin/module/create");
		List<User> listReturn = new ArrayList<User>();
		List<User> listUsers = userService.getAllUser();
		for(int i = 0; i < listUsers.size(); i++){
			User user = listUsers.get(i);
			if(user.getStatut().equals(Statut.RESPO_MODULE)){
				listReturn.add(user);
			}
		}
		modelAndView.addObject("users", listReturn);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/create/send", method = RequestMethod.POST)
	public ModelAndView createModuleSend(@RequestParam String name, 
			@RequestParam String domain,
			@RequestParam(required = false) String description,
			@RequestParam(required = false) MultipartFile image,
			@RequestParam(required = false) long user_id,
			RedirectAttributes redirectAttributes, 
			ModelAndView modelAndView,
			HttpSession session){
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
									WacajouApplication.ROOT + "/images/module/"
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
		modelAndView.setViewName("forward:../../administration");
		moduleService.Create(name, description, full_file_name, domain, userService.getUser(user_id));
		if(moduleService.getError() != null )
			modelAndView.addObject("error", moduleService.getError());
		else
			modelAndView.addObject("success", "Le module à été créer avec succès");
		return modelAndView;	
	}
	
	@RequestMapping(value = "/consult")
	public ModelAndView consultModule(@RequestParam String name){
		ModelAndView modelAndView = new ModelAndView();
		Module module = moduleService.getByName(name);
		if( module != null ){
			try{
				List<Comments> comments = moduleService.getComments(module);
				modelAndView.addObject("comments", comments);
				modelAndView.addObject("commentAverage", moduleService.getAverage(module, comments));
			}catch(Exception e){
				e.printStackTrace();
			}
			modelAndView.addObject("moduleConsult", module);
			modelAndView.setViewName("consult/module");
		}else
			modelAndView.setViewName("redirect:../../home");
		return modelAndView;		
	}
	
	@RequestMapping(value = "/edit")
	public ModelAndView editModule(@RequestParam Long id, ModelAndView modelAndView){
		Module module = moduleService.getOne(id);
		List<User> listReturn = new ArrayList<User>();
		List<User> listUsers = userService.getAllUser();
		for(int i = 0; i < listUsers.size(); i++){
			User user = listUsers.get(i);
			if(user.getStatut().equals(Statut.RESPO_MODULE)){
				listReturn.add(user);
			}
		}
		modelAndView.addObject("users", listReturn);
		modelAndView.addObject("module", module);		
		modelAndView.setViewName("edit/module");
		return modelAndView;
	}
	
	@RequestMapping(value = "/edit/process", method = RequestMethod.POST)
	public ModelAndView editModuleSave(@ModelAttribute Module module, ModelAndView modelAndView){
		moduleService.Update(module);
		modelAndView.setViewName("redirect:../../administration");
		return modelAndView;
	}
	
	@RequestMapping(value = "/inscription", method = RequestMethod.POST)
	public ModelAndView inscriptionModule(@ModelAttribute("user") User user, @RequestParam("parcours") Parcours parcours, ModelAndView modelAndView){
		if(user.isConnect()){
			List<Module> modules = moduleService.getByParcours(parcours);
			List<Module> modules_optional = moduleService.getByParcoursOptional(parcours);
			modelAndView.addObject("modules", modules);
			modelAndView.addObject("modules_optional", modules_optional);
			modelAndView.addObject("parcours", parcours);
			modelAndView.setViewName("inscription/module");
		}else{
			modelAndView.setViewName("redirect:../../home");
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/inscription/process", method = RequestMethod.POST)
	public ModelAndView inscriptionModuleProcess(@ModelAttribute("userParcours") Parcours parcours, @ModelAttribute("user") User user, @RequestParam("modules") List<Long> modules, ModelAndView modelAndView){
		if(user.isConnect()){
			List<Module> module = new ArrayList<Module>();
			for(Long id: modules)
				module.add(moduleService.getOne(id));
			userService.setUserModule(user, parcours, module);
			modelAndView.addObject("userModule", userService.getUserModule(user));
			modelAndView.setViewName("user/profil");
		}else{
			modelAndView.setViewName("redirect:../../../home");
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public ModelAndView setComment(@ModelAttribute("user") User user, 
			@RequestParam("name") String name, 
			@RequestParam("title") String title, 
			@RequestParam("message") String message, 
			@RequestParam("rating") Rating rating, 
			ModelAndView modelAndView){
		if(user.isConnect()){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("tilte", title);
			map.put("message", message);
			map.put("rating", rating);
			Module module = moduleService.getByName(name);
			moduleService.Evaluate(module, user, map);
			modelAndView.addObject("moduleConsult", module);
			modelAndView.setViewName("forward:../../module/consult");
		}else
			modelAndView.setViewName("redirect:../../home");
		return modelAndView;		
	}
} 
