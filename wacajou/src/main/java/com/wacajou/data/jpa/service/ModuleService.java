package com.wacajou.data.jpa.service;

import java.util.List;

import org.hibernate.service.spi.ServiceException;

import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.User;

public interface ModuleService {

	void Create(String name, String description, String image, String domain,
			String user_id) throws ServiceException;

	void Create(String name, String description, String image, String domain,
			User user) throws ServiceException;

	void Update(String name) throws ServiceException;

	void Delete(String name) throws ServiceException;

	List<Module> getAllModule() throws ServiceException;

	List<Module> getAllModuleByDomain(String domain) throws ServiceException;

	Module getByRespofNameAndlName(String fname, String lname)
			throws ServiceException;

	Module ConsultByName(String name) throws ServiceException;

	Object getError();

}