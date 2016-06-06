package com.wacajou.data.jpa.service;

import java.util.List;

import org.hibernate.service.spi.ServiceException;

import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.Parcours;
import com.wacajou.data.jpa.domain.User;

public interface ParcoursService {
	void Update(Parcours parcours) throws ServiceException;

	Parcours Consult(String parcoursName) throws ServiceException;

	void setParcoursModule(Parcours parcours, Module module, boolean optional)
			throws ServiceException;

	void Create(String name, String description, String image, String domain,
			List<Long> modules, List<Long> modules_optional, User user)
			throws ServiceException;

	List<Parcours> getAll() throws ServiceException;

	String getError();

}