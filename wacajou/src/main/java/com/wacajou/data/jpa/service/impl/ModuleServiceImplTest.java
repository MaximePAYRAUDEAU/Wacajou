package com.wacajou.data.jpa.service.impl;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.repository.ModuleRepository;
import com.wacajou.data.jpa.service.ModuleService;

@Service
@Transactional
public class ModuleServiceImplTest implements ModuleService{
	
	@Autowired
    private ModuleRepository moduleRepository;
	
	@Override
	public void Create(String name, String domain) throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Module Consult(String moduleName) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void Update(Module module) throws ServiceException {
		// TODO Auto-generated method stub
		
	}

}
