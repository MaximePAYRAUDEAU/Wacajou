package com.wacajou.data.jpa.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.wacajou.data.jpa.domain.Comments;
import com.wacajou.data.jpa.domain.Domain;
import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.Parcours;
import com.wacajou.data.jpa.domain.ParcoursModule;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.repository.ModuleRepository;
import com.wacajou.data.jpa.repository.ParcoursModuleRepository;
import com.wacajou.data.jpa.repository.ParcoursRepository;
import com.wacajou.data.jpa.repository.UserRepository;
import com.wacajou.data.jpa.service.ParcoursService;

@Component("parcoursService")
@Transactional
public class ParcoursServiceImpl extends CommentServiceImpl<Parcours> implements ParcoursService {

	private String error = null;

	@Autowired
	private ParcoursRepository parcoursRepository;
	@Autowired
	private ModuleRepository moduleRepository;
	@Autowired 
	private ParcoursModuleRepository parcoursModuleRepository;
	@Autowired 
	private UserRepository userRepository;

	public String getError() {
		return error;
	}

	@Override
	public Parcours getByName(String parcoursName) throws ServiceException {
		Parcours parcours = parcoursRepository.findByName(parcoursName);
		if (parcours == null) {
			error = "Module inexistant";
		}
		return parcours;
	}

	@Override
	public void setParcoursModule(Parcours parcours, Module module, boolean optional) throws ServiceException {
		parcoursModuleRepository.save(new ParcoursModule(parcours, module, optional));
	}

	/*@Override
	public void Create(String name, String description, String image, String domain, List<Long> modules, List<Long> modules_optional,
			User user) throws ServiceException {
		Assert.notNull(name);
		Assert.notNull(domain);

		Parcours parcours = new Parcours();

		Domain[] tab = Domain.values();
		for (int i = 0; i < tab.length; i++) {
			if (tab[i].toString().equals(domain)) {
				parcours.Create(name, description, image, tab[i]);
				break;
			}
		}
		if (parcours.getDomain() == null) {
			error = "Domain non conforme";
			return;
		}

		if (modules != null)
			for (int i = 0; i < modules.size(); i++) {
				Module module = moduleRepository.findOne(modules.get(i));
				parcoursModuleRepository.save(new ParcoursModule(parcours, module, false));
			}
		
		if (modules_optional != null)
			for (int i = 0; i < modules_optional.size(); i++) {
				Module module = moduleRepository.findOne(modules_optional.get(i));
				parcoursModuleRepository.save(new ParcoursModule(parcours, module, true));
			}

		if (user != null)
			parcours.setRespo(user);
		else
			parcours.setRespo(userRepository.findOne(0L));
		
		parcoursRepository.save(parcours);
	}*/

	@Override
	public List<Parcours> getAll() throws ServiceException {
		try{
			return parcoursRepository.findAll();
		}catch(Exception e){
			error = "Not able to find all parcours.";
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Module> getModulesPrincipaux(Parcours parcours)
			throws ServiceException {
		List<ParcoursModule> parcoursModule = parcoursModuleRepository.findByParcoursAndOptional(parcours, false);
		
		List<Module> module = new ArrayList<Module>();
		for(int i = 0; i < parcoursModule.size(); i++)
			module.add(moduleRepository.findByParcoursModule(parcoursModule.get(i)));
		return module;
	}

	@Override
	public List<Module> getModulesOptional(Parcours parcours)
			throws ServiceException {
		List<ParcoursModule> parcoursModule = parcoursModuleRepository.findByParcoursAndOptional(parcours, true);
		
		List<Module> module = new ArrayList<Module>();
		for(int i = 0; i < parcoursModule.size(); i++)
			module.add(moduleRepository.findByParcoursModule(parcoursModule.get(i)));
		return module;
	}

	@Override
	public Parcours getByRespo(User user) throws ServiceException {
		return parcoursRepository.findByRespo(user);
	}

	@Override
	public void Create(HashMap<String,Object> map) throws ServiceException {
		String name = (String) map.get("name");
		String description = (String) map.get("description");
		String domain = (String) map.get("domain");
		String image = (String) map.get("image");
		List<Long> modules = (List<Long>) map.get("module");
		List<Long> modules_optional = (List<Long>) map.get("module_optional");
		User user = (User) map.get("user");
		
		Parcours parcours = new Parcours();

		Domain[] tab = Domain.values();
		for (int i = 0; i < tab.length; i++) {
			if (tab[i].toString().equals(domain)) {
				parcours.Create(name, description, image, tab[i]);
				break;
			}
		}
		if (parcours.getDomain() == null) {
			error = "Domain non conforme";
			return;
		}

		if (modules != null)
			for (Long id: modules) {
				Module module = moduleRepository.findOne(id);
				parcoursModuleRepository.save(new ParcoursModule(parcours, module, false));
			}
		
		if (modules_optional != null)
			for (Long id: modules_optional) {
				Module module = moduleRepository.findOne(id);
				parcoursModuleRepository.save(new ParcoursModule(parcours, module, true));
			}

		if (user != null)
			parcours.setRespo(user);
		else
			parcours.setRespo(userRepository.findOne(0L));
		try{
			parcoursRepository.save(parcours);
		}catch(Exception e){
			error = "Error create parcours";
		}
		
	}

	@Override
	public void Update(Parcours parcours, HashMap<String,Object> map) throws ServiceException {
		String description = (String) map.get("description");
		String image = (String) map.get("image");
		List<Long> modules = (List<Long>) map.get("modules");
		List<Long> modules_optional = (List<Long>) map.get("modules_optional");
		User user = (User) map.get("user");		
		if(image != null)
			parcours.setImage(image);
		if(description != null)
			parcours.setDescription(description);
		if (modules != null)
			for (Long id: modules) {
				Module module = moduleRepository.findOne(id);
				if(parcoursModuleRepository.findByParcoursAndModuleAndOptional(parcours, module, false) != null)
					parcoursModuleRepository.save(new ParcoursModule(parcours, module, false));
			}
		
		if (modules_optional != null)
			for (Long id: modules_optional) {
				Module module = moduleRepository.findOne(id);
				if(parcoursModuleRepository.findByParcoursAndModuleAndOptional(parcours, module, true) != null)
					parcoursModuleRepository.save(new ParcoursModule(parcours, module, true));
			}

		if (user != null)
			parcours.setRespo(user);
		try{
			parcoursRepository.save(parcours);
		}catch(Exception e){
			error = "Update error";
		}
	}

	@Override
	public void Delete(Parcours parcours) throws ServiceException {
		try{
			parcoursRepository.delete(parcours);
		}catch(Exception e){
			error = "Delete error";
		}
	}

}