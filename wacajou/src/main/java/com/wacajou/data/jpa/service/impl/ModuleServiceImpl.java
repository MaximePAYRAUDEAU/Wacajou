package com.wacajou.data.jpa.service.impl;

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
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.repository.ModuleRepository;
import com.wacajou.data.jpa.repository.UserRepository;
import com.wacajou.data.jpa.service.ModuleService;

@Component("moduleService")
@Transactional
public class ModuleServiceImpl extends CommentServiceImpl<Module> implements ModuleService {

	private String error = null;

	@Autowired
	Environment env;
	@Autowired
	private ModuleRepository moduleRepository;
	@Autowired
	private UserRepository userRepository;
	
	public String getError() {
		return error;
	}

	@Override
	public List<Module> getAll() throws ServiceException {
		error = null;
		return moduleRepository.findAll();
	}

	@Override
	public List<Module> getAllModuleByDomain(String domain)
			throws ServiceException {
		error = null;
		Domain[] tab = Domain.values();
		for (int i = 0; i < tab.length; i++) {
			if (tab[i].toString().equals(domain)) {
				return moduleRepository.findByDomain(tab[i]);
			}
		}
		return null;
	}

	@Override
	public Module getByRespo(User user)
			throws ServiceException {
		return moduleRepository.findByRespo(user);
	}

	@Override
	public Module getByName(String name) throws ServiceException {
		error = null;
		Module module = moduleRepository.findByName(name);
		if (module == null) {
			error = "Module inexistant";
		}
		return module;		
	}

	@Override
	public void Create(HashMap<String, Object> map) throws ServiceException {
		Module module = new Module();
		Domain[] tab = Domain.values();
		for (int i = 0; i < tab.length; i++) {
			if (tab[i].toString().equals(map.get("domain"))) {
				module.Create((String) map.get("name"), (String) map.get("description"), (String) map.get("image"), (String) map.get("code"), tab[i]);
				break;
			}
		}
		if (module.getDomain() == null)
			error = "Domain non conforme";
		if(map.containsKey("user"))
			module.setRespo((User) map.get("user"));
		if(map.containsKey("semester"))
			module.setSemester((String) map.get("semester"));
		if(map.containsKey("tp_cours"))
			module.setTpCours((int) map.get("tp_cours"));
		if(map.containsKey("project"))
			module.setProject((int) map.get("project"));
		if(map.containsKey("ects"))
			module.setEcts((double) map.get("ects"));
		if(map.containsKey("link"))
			module.setLink((String) map.get("link"));
		
		try {
			moduleRepository.save(module);
		} catch (Exception e) {
			e.printStackTrace();
			error = "Module déjà existant";
		}		
	}

	@Override
	public void Update(Module t, HashMap<String, Object> map)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Delete(Module t) throws ServiceException {
		moduleRepository.delete(t);
	}

	@Override
	public Module getOne(Long id) throws ServiceException {
		return moduleRepository.getOne(id);
	}

	@Override
	public void Update(Module module) {
		try{
			moduleRepository.save(module);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public List<Module> getByParcours(Parcours parcours)
			throws ServiceException {
		return moduleRepository.findByParcours(parcours);
	}

	@Override
	public List<Module> getByParcoursOptional(Parcours parcours)
			throws ServiceException {
		return null; //moduleRepository.findByParcoursAndOptional(parcours);;
	}

	@Override
	public void Create(Module module) 
			throws ServiceException {
		try{
			moduleRepository.save(module);
		}catch(Exception e){
			throw new ServiceException("Create module failed : ", e);
		}
	}

}