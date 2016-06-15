package com.wacajou.data.jpa.service;

import java.util.List;

import org.hibernate.service.spi.ServiceException;

import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.Parcours;

public interface ParcoursService extends GenericService<Parcours> {
	
/*	void Create(String name, String description, String image, String domain,
			List<Long> modules, List<Long> modules_optional, User user)
			throws ServiceException;*/
	
//	void Update(Parcours parcours) throws ServiceException;

//	Parcours Consult(String name) throws ServiceException;

	
//	List<Parcours> getAll() throws ServiceException;
	
	List<Module> getModulesPrincipaux(Parcours parcours) throws ServiceException;

	List<Module> getModulesOptional(Parcours parcours) throws ServiceException;

	void setParcoursModule(Parcours parcours, Module module, boolean optional) throws ServiceException;

	String getError();
}