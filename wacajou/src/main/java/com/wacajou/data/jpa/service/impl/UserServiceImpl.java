package com.wacajou.data.jpa.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.Parcours;
import com.wacajou.data.jpa.domain.Statut;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.domain.UserInfo;
import com.wacajou.data.jpa.domain.UserModule;
import com.wacajou.data.jpa.domain.UserParcours;
import com.wacajou.data.jpa.repository.ModuleRepository;
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
public class UserServiceImpl implements UserService {

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
	
	private String error = null;

	public String getError() {
		return this.error;
	}

	@Override
	public void Create(String login, String promo, String statut)
			throws ServiceException {
		// Vérification valeurs non null
		Assert.notNull(login);
		Assert.notNull(promo);
		Assert.notNull(statut);
		error = null;

		User user = new User();
		Statut[] tab = Statut.values();
		for (int i = 0; i < tab.length; i++) {
			if (tab[i].toString().equals(statut)) {
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
			userInfoRepository.save(userInfo);
		} catch (Exception e) {
			e.printStackTrace();
			error = "Utilisateur déjà existant";
		}
	}

	@Override
	public User Connexion(String login, String mdp) throws ServiceException {
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
		error = null;
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
		return userInfoRepository.findByUser(user);
	}

	@Override
	public Parcours getUserParcours(User user) throws ServiceException {
		UserParcours userParcours = userParcoursRepository.findByUser(user);
		return parcoursRepository.findByUserParcours(userParcours);
	}

	@Override
	public List<Module> getUserModule(User user) throws ServiceException {
		List<UserModule> userModule = userModuleRepository.findByUser(user);
		List<Module> modules = new ArrayList<Module>();
		for(int i = 0; i < userModule.size(); i++)
			modules.add(moduleRepository.findByUserModule(userModule.get(i)));
		return modules;
	}

}
