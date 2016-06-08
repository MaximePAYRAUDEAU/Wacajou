package com.wacajou.data.jpa.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
public class ParcoursServiceImpl implements ParcoursService {

	private String error = null;

	@Autowired
	Environment env;

	@Autowired
	private ParcoursRepository parcoursRepository;
	@Autowired
	private ModuleRepository moduleRepository;
	
	@Autowired 
	private ParcoursModuleRepository parcoursModuleRepository;
	@Autowired 
	private UserRepository userRepository;

	/*@Autowired
	public ParcoursServiceImpl(ParcoursRepository parcoursRepository, 
			ModuleRepository moduleRepository,
			ParcoursModuleRepository parcoursModuleRepository, 
			UserRepository userRepository) {
		this.parcoursRepository = parcoursRepository;
		this.moduleRepository = moduleRepository;
		this.parcoursModuleRepository = parcoursModuleRepository;
		this.userRepository = userRepository;
	}*/

	public String getError() {
		return error;
	}

	@Override
	public Parcours Consult(String parcoursName) throws ServiceException {
		Parcours parcours = parcoursRepository.findByName(parcoursName);
		if (parcours.equals(null)) {
			error = "Module inexistant";
		}
		return parcours;
	}

	@Override
	public void Update(Parcours parcours) throws ServiceException {
		int version = parcours.getVersion();
		parcours.setVersion(version + 1);
		// String path = parcours.getPath();
		// String save = path.split("_V")[0] + parcours.getVersion();
		// parcours.setPath(save);
		parcoursRepository.save(parcours);
	}

	public Module getModule(String moduleName) throws ServiceException {
		return moduleRepository.findByName(moduleName);
	}

	@Override
	public void setParcoursModule(Parcours parcours, Module module, boolean optional) throws ServiceException {
		parcoursModuleRepository.save(new ParcoursModule(parcours, module, optional));
	}

	public List<Module> getAllModule() {
		List<Module> modules = new ArrayList<Module>();
		try {
			modules = moduleRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modules;
	}

	@Override
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
	}

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
}