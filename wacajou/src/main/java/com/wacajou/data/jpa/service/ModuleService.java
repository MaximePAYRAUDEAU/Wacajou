package com.wacajou.data.jpa.service;

import org.hibernate.service.spi.ServiceException;

import com.wacajou.data.jpa.domain.Module;

public interface ModuleService {
	void Create(String name, String description, String image, String domain) throws ServiceException;
	Module Consult(String moduleName) throws ServiceException;
	void Update(String name) throws ServiceException;
	void Delete(String name) throws ServiceException;

}