package com.wacajou.data.jpa.service.impl;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.wacajou.data.jpa.domain.Domain;
import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.Statut;
import com.wacajou.data.jpa.repository.ModuleRepository;
import com.wacajou.data.jpa.service.ModuleService;

@Component("moduleService")
@Transactional
public class ModuleServiceImpl implements ModuleService {

	private String error = null;
	
	@Autowired
	Environment env;

	private final ModuleRepository moduleRepository;

	@Autowired
	public ModuleServiceImpl(ModuleRepository moduleRepository) {
		this.moduleRepository = moduleRepository;
	}
	
	@Override
	public void Create(String name, String description, String image,
			String domain) throws ServiceException {
		// TODO Auto-generated method stub
		Assert.notNull(name);
		Assert.notNull(domain);
		Module module = new Module();
		Domain[] tab = Domain.values();
		for (int i = 0; i < tab.length; i++) {
			if (tab[i].toString().equals(domain)) {
				module.Create(name, description, image, tab[i]);
				break;
			}
		}
		if(module.getDomain() == null){
			error = "Domain non conforme";
		}
		try {
			moduleRepository.save(module);
		} catch (Exception e) {
			e.printStackTrace();
			error = "Module déjà existant";
		}
		
	}

	public String getError() {
		return error;
	}
	
	@Override
	public Module Consult(String moduleName) throws ServiceException {
		Module module = moduleRepository.findByName(moduleName);
		if(module.equals(null)){
			error = "Module inexistant";
		}
		return module;
	}

	@Override
	public void Update(String name) throws ServiceException {
		try{
			Module module = moduleRepository.findByName(name);
			module.setDescription("updated desc");
			moduleRepository.saveAndFlush(module);
		}catch (Exception e) {
			e.printStackTrace();
			error = "Erreur dans l'update";
		}
	}

	@Override
	public void Delete(String name) throws ServiceException {
		Module module = Consult(name);
		try{
			moduleRepository.delete(module);
		}catch (Exception e){
			e.printStackTrace();
			error = "Erreur lors de la suppression du module";
		}
	}

	
}