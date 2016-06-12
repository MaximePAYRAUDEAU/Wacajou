package com.wacajou.data.jpa.service;

import java.util.List;

import org.hibernate.service.spi.ServiceException;

import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.User;
/**
 * <h1>Service de gestion des modules
 * 
 * @author Payraudeau Maxime
 *
 */
public interface ModuleService extends GenericService<Module> {
	/**
	 * <h1>Création d'un module avec l'ensemble des informations
	 * 
	 * @param name
	 * @param description
	 * @param image
	 * @param domain
	 * @param user_id
	 * @throws ServiceException
	 */
	void Create(String name, String description, String image, String domain,
			String user_id) throws ServiceException;

	void Create(String name, String description, String image, String domain,
			User user) throws ServiceException;

	void Update(Module module, String name, String description, String image, String domain,
			User user) throws ServiceException;

	void Delete(String name) throws ServiceException;

	/**
	 * <h1>Recupération de tout les modules existants
	 * 
	 * @return All module on database
	 * @throws ServiceException
	 */
//	List<Module> getAll() throws ServiceException;

	/**
	 * <h1>Recupération de tout les modules d'un domain
	 * 
	 * @param domain
	 * @return	All module on database where domain = @Param domain
	 * @throws ServiceException
	 */
	List<Module> getAllModuleByDomain(String domain) throws ServiceException;

//	Module getByRespo(User respo) throws ServiceException;

//	Module ConsultByName(String name) throws ServiceException;

	String getError();

	void Update(Module module);

}