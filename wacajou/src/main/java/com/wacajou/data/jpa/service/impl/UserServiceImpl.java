package com.wacajou.data.jpa.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.Parcours;
import com.wacajou.data.jpa.domain.ParcoursModule;
import com.wacajou.data.jpa.domain.Statut;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.domain.UserInfo;
import com.wacajou.data.jpa.domain.UserModule;
import com.wacajou.data.jpa.domain.UserParcours;
import com.wacajou.data.jpa.repository.ModuleRepository;
import com.wacajou.data.jpa.repository.ParcoursModuleRepository;
import com.wacajou.data.jpa.repository.ParcoursRepository;
import com.wacajou.data.jpa.repository.UserInfoRepository;
import com.wacajou.data.jpa.repository.UserModuleRepository;
import com.wacajou.data.jpa.repository.UserParcoursRepository;
import com.wacajou.data.jpa.repository.UserRepository;
import com.wacajou.data.jpa.service.UserService;
import com.wacajou.ldap.LDAPObject;
import com.wacajou.ldap.LDAPaccess;

@Component("userService")
@Transactional
public class UserServiceImpl extends CommentServiceImpl<User> implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private UserParcoursRepository userParcoursRepository;
	
	@Autowired
	private UserModuleRepository userModuleRepository;
	
	@Autowired
	private ModuleRepository moduleRepository;
	
	@Autowired
	private ParcoursRepository parcoursRepository;
	
	@Autowired
	private ParcoursModuleRepository parcoursModuleRepository;
	
	private String error = null;

	public String getError() {
		return this.error;
	}

	@Override
	public void Create(String login, String promo, Statut statut, Parcours parcours)
			throws ServiceException {
		// Vérification valeurs non null
		Assert.notNull(login);
		Assert.notNull(promo);
		Assert.notNull(statut);
		error = null;

		User user = new User();
		Statut[] tab = Statut.values();
		for (int i = 0; i < tab.length; i++) {
			if (tab[i].equals(statut)) {
				user.Create(login, promo, tab[i]);
				break;
			}
		}
		if (user.getStatut() == null) {
			error = "Statut non conforme";
		}
		UserInfo userInfo = new UserInfo();
		userInfo.setUser(user);
		try {
			userRepository.save(user);
			try{
				userInfoRepository.save(userInfo);
				try{
					userParcoursRepository.save(new UserParcours(user, parcours));
				}catch(Exception e){
					if(parcours == null)
						e.printStackTrace();
					else
						error = "Setting parcours non attribué pour l'utilisateur " + login;
				}
			}catch(Exception e){
				error = "Utilisateur " + login + " déjà existant";
			}	
		} catch (Exception e) {
			e.printStackTrace();
			error = "Utilisateur " + login + " déjà existant";
		}
	}

	@Override
	public User Connect(String login, String mdp) throws ServiceException {
		// Need to be tested
		error = null;
		LDAPaccess access = new LDAPaccess();
		try {
			LDAPObject userLdap = access.LDAPget(login, mdp);
			if (userLdap == null) {
				error = "Login invalide";
				return null;
			}
			User userDb = userRepository.findByLogin(login);
			if(userDb == null){
				error = "Utilisateur non inscrit dans la base de donnée. Veuillez contacter votre responsable de parcours.";
				return null;
			}
			if((userDb.getFname() == null)|| (userDb.getLname() == null))
				userDb.Complete(userLdap.getNumber(), userLdap.getPrenom(), userLdap.getNomFamille(), userLdap.getMail(), userLdap.getType());
			return userDb;
		} catch (Exception e) {
			error = "Connexion impossible. Veuillez contacter l'administrateur.";
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public User getUser(String user_reciver_name) throws ServiceException {
		error = null;
		String[] name = user_reciver_name.split("_");
		User user = new User();
		try {
			user = userRepository.findByFnameAndLname(name[0], name[1]);
		} catch (Exception e) {
			error = "Error finding user with name :" + name[0]
					+ " and lastname : " + name[1] + ".";
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public User getUserByLogin(String login) throws ServiceException {
		error = null;
		try {
			return userRepository.findByLogin(login);
		} catch (Exception e) {
			error = "Error user not found";
			return null;
		}
	}

	@Override
	public List<User> getAllUser() throws ServiceException {
		error = null;
		try {
			return userRepository.findAll();
		} catch (Exception e) {
			error = "Erreur";
			return null;
		}
	}

	@Override
	public User getUser(long user_id) throws ServiceException {
		return userRepository.getOne(user_id);
	}

	@Override
	public User ConnexionAlwayTrue(String login) throws ServiceException {
		error = null;
		User userDb = userRepository.findByLogin(login);
		if(userDb == null){
			error = "Utilisateur non inscrit dans la base de donnée. Veuillez contacter votre responsable de parcours.";
			return null;
		}
		return userDb;
	}

	@Override
	public UserInfo getInfos(User user) throws ServiceException {
		UserInfo info = userInfoRepository.findByUser(user);
		if(info == null ){
			info = new UserInfo();
			info.setUser(user);
			userInfoRepository.save(info);
		}
		return info;
	}

	@Override
	public Parcours getUserParcours(User user) throws ServiceException {
		return parcoursRepository.findByUser(user);
	}

	@Override
	public List<Module> getUserModule(User user) throws ServiceException {
		List<UserModule> userModule = userModuleRepository.findByUser(user);
		List<Module> modules = new ArrayList<Module>();
		for(int i = 0; i < userModule.size(); i++)
			modules.add(moduleRepository.findByUserModule(userModule.get(i)));
		return modules;
	}

	@Override
	public void updateInfo(User user, String full_file_name, String file_name)
			throws ServiceException {
		UserInfo info = userInfoRepository.findByUser(user);
		System.out.println("Info : " + info.getId() );
		System.out.println("File : " + file_name + " FileName : " + full_file_name );
		if(file_name.equals("cv"))
			info.setCv(full_file_name);
		else if(file_name.equals("ldm"))
			info.setLdm(full_file_name);
		else if(file_name.equals("mark"))
			info.setMark(full_file_name);
		else if(file_name.equals("internship"))
			info.setInternship(full_file_name);
		else if(file_name == "image")
			info.setImage(full_file_name);
		else if(file_name.equals("university"))
			info.setUniversity(full_file_name);
		try{
			userInfoRepository.save(info);
		}catch(Exception e){
			error = "Unable to save informations.";
			e.printStackTrace();
		}
	}

	@Override
	public void setUserParcours(User user, Parcours parcours) throws ServiceException {
		try{
			userParcoursRepository.save(new UserParcours(user, parcours));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void setUserModule(User user, Parcours parcours, List<Module> module_optional)
			throws ServiceException {
		try{
			if(parcours != null){
				List<ParcoursModule> module_non_optional = parcoursModuleRepository.findByParcours(parcours);
				for(ParcoursModule parcoursModule: module_non_optional)
					module_optional.add(moduleRepository.findByParcoursModule(parcoursModule));
			}
			for(Module module: module_optional)
				userModuleRepository.save(new UserModule(user, module));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Create(HashMap<String, Object> map) throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Update(User t, HashMap<String, Object> map)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Delete(User t) throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User getByRespo(User user) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getByName(String name) throws ServiceException {
		return null;
	}

	@Override
	public List<User> getAll() throws ServiceException {
		return userRepository.findAll();
	}

	@Override
	public User getOne(Long id) throws ServiceException {
		return userRepository.getOne(id);
	}

	@Override
	public List<User> getUserByParcours(Parcours parcours) throws ServiceException {
		return userRepository.findByParcours(parcours);
	}

	@Override
	public List<User> getUserByModule(Module module) throws ServiceException {
		return userRepository.findByModule(module);
	}

}
