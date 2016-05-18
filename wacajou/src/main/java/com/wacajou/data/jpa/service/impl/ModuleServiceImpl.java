package com.wacajou.data.jpa.service.impl;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.wacajou.data.jpa.domain.Module;
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
	public void Create(String name, String domain) throws ServiceException {
		// Vérifiaction valeur non null

		Assert.notNull(name);
		Assert.notNull(domain);

		Module module = new Module(name, name + "_V0", domain);

		try {
			moduleRepository.save(module);
		} catch (Exception e) {
			e.printStackTrace();
			error = "Duplicate module";
			throw new ServiceException("Duplicate module");
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
	public void Update(Module module) throws ServiceException {
		int version = module.getVersion();
		module.setVersion(version + 1);
		String path = module.getPath();
		String save = path.split("_V")[0] + module.getVersion();
		module.setPath(save);
		moduleRepository.save(module);		
	}
}