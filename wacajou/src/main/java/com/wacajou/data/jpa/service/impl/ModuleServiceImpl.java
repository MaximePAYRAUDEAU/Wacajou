package com.wacajou.data.jpa.service.impl;

import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.wacajou.data.jpa.domain.Domain;
import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.repository.ModuleRepository;
import com.wacajou.data.jpa.repository.UserRepository;
import com.wacajou.data.jpa.service.ModuleService;

@Component("moduleService")
@Transactional
public class ModuleServiceImpl implements ModuleService {

	private String error = null;

	@Autowired
	Environment env;
	@Autowired
	private ModuleRepository moduleRepository;
	@Autowired
	private UserRepository userRepository;
	
	//@Autowired
	/*public ModuleServiceImpl(ModuleRepository moduleRepository, UserRepository userRepository) {
		this.userRepository = userRepository;
		this.moduleRepository = moduleRepository;
	}*/

	@Override
	public void Create(String name, String description, String image,
			String domain, String user_id) throws ServiceException {
		error = null;
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

		if (module.getDomain() == null) {
			error = "Domain non conforme";
		}
		if (user_id != null) {
			String[] user_info = user_id.split(",");
			String fname = user_info[0];
			String lname = user_info[1];
			User user = this.userRepository.findByFnameAndLname(fname, lname);
			module.setRespo(user);
		} else
			module.setRespo(this.userRepository.findByLogin("admin"));
		try {
			this.moduleRepository.save(module);
		} catch (Exception e) {
			e.printStackTrace();
			error = "Module déjà existant";
		}

	}

	public String getError() {
		return error;
	}

	@Override
	public void Update(String name) throws ServiceException {
		error = null;
		try {
			Module module = this.moduleRepository.findByName(name);
			if (module != null) {
				module.setDescription("updated desc");
				this.moduleRepository.saveAndFlush(module);
			}else{
				error = "Module inexistant";
			}
		} catch (Exception e) {
			e.printStackTrace();
			error = "Erreur dans l'update";
		}
	}

	@Override
	public void Delete(String name) throws ServiceException {
		error = null;
		Module module = ConsultByName(name);
		try {
			this.moduleRepository.delete(module);
		} catch (Exception e) {
			e.printStackTrace();
			error = "Erreur lors de la suppression du module";
		}
	}

	@Override
	public List<Module> getAllModule() throws ServiceException {
		error = null;
		return this.moduleRepository.findAll();
	}

	@Override
	public List<Module> getAllModuleByDomain(String domain)
			throws ServiceException {
		error = null;
		Domain[] tab = Domain.values();
		for (int i = 0; i < tab.length; i++) {
			if (tab[i].toString().equals(domain)) {
				return this.moduleRepository.findByDomain(tab[i]);
			}
		}
		return null;
	}

	@Override
	public Module getByRespofNameAndlName(String fname, String lname)
			throws ServiceException {
		error = null;
		User user = this.userRepository.findByFnameAndLname(fname, lname);
		return this.moduleRepository.findByRespo(user);
	}

	@Override
	public Module ConsultByName(String name) throws ServiceException {
		error = null;
		Module module = this.moduleRepository.findByName(name);
		if (module.equals(null)) {
			error = "Module inexistant";
		}
		return module;
	}

	@Override
	public void Create(String name, String description, String image,
			String domain, User user) throws ServiceException {
		error = null;
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

		if (module.getDomain() == null) {
			error = "Domain non conforme";
		}
		if (user != null) {
			module.setRespo(user);
		} else
			module.setRespo(this.userRepository.findByLogin("admin"));
		try {
			this.moduleRepository.save(module);
		} catch (Exception e) {
			e.printStackTrace();
			error = "Module déjà existant";
		}

	}

}