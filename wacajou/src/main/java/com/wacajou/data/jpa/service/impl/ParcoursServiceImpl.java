package com.wacajou.data.jpa.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.Parcours;
import com.wacajou.data.jpa.domain.ParcoursModule;
import com.wacajou.data.jpa.repository.ModuleRepository;
import com.wacajou.data.jpa.repository.ParcoursModuleRepository;
import com.wacajou.data.jpa.repository.ParcoursRepository;
import com.wacajou.data.jpa.service.ParcoursService;

@Component("parcoursService")
@Transactional
public class ParcoursServiceImpl implements ParcoursService {

	private String error = null;
	
	@Autowired
	Environment env;

	private final ParcoursRepository parcoursRepository;
	private final ModuleRepository moduleRepository;
	private final ParcoursModuleRepository parcoursModuleRepository;
	@Autowired
	public ParcoursServiceImpl(ParcoursRepository parcoursRepository, ModuleRepository moduleRepository, ParcoursModuleRepository parcoursModuleRepository) {
		this.parcoursRepository = parcoursRepository;
		this.moduleRepository = moduleRepository;
		this.parcoursModuleRepository = parcoursModuleRepository;
	}

	@Override
	public Parcours Create(String name) throws ServiceException {
		// Vérifiaction valeur non null

		Assert.notNull(name);

		
		Parcours parcours = new Parcours(name, name + "_V0");
		try {
			parcoursRepository.save(parcours);
			return parcours;
		} catch (Exception e) {
			e.printStackTrace();
			error = "Duplicate parcours";
			throw new ServiceException("Duplicate parcours");
		}

	}

	public String getError() {
		return error;
	}
	
	@Override
	public Parcours Consult(String parcoursName) throws ServiceException {
		Parcours parcours = parcoursRepository.findByName(parcoursName);
		if(parcours.equals(null)){
			error = "Module inexistant";
		}
		return parcours;
	}

	@Override
	public void Update(Parcours parcours) throws ServiceException {
		int version = parcours.getVersion();
		parcours.setVersion(version + 1);
		String path = parcours.getPath();
		String save = path.split("_V")[0] + parcours.getVersion();
		parcours.setPath(save);
		parcoursRepository.save(parcours);		
	}
	
	public Module getModule(String moduleName) throws ServiceException {
		return moduleRepository.findByName(moduleName);
	}
	
	@Override
	public void setParcoursModule(Parcours parcours, Module module, boolean optional)throws ServiceException{
		parcoursModuleRepository.save(new ParcoursModule(parcours, module, optional));
	}

	public List<Module> getAllModule() {
		List<Module> modules = new ArrayList<Module>();
		try{
			modules = moduleRepository.findAll();
		}catch(Exception e){
			e.printStackTrace();
		}
		return modules;
	}
}