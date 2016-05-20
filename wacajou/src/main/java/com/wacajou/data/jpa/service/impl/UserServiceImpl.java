package com.wacajou.data.jpa.service.impl;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.wacajou.data.jpa.domain.Statut;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.repository.UserRepository;
import com.wacajou.data.jpa.service.UserService;

@Component("userService")
@Transactional
public class UserServiceImpl implements UserService {

	
	private final UserRepository userRepository;

	private String error = null;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void Create(String login, String promo, String statut) throws ServiceException {
		// Vérification valeurs non null
		Assert.notNull(login);
		Assert.notNull(promo);
		Assert.notNull(statut);
		User user = new User();
		Statut[] tab = Statut.values();
		for (int i = 0; i < tab.length; i++) {
			if (tab[i].toString().equals(statut)) {
				user.Create(login, promo, tab[i]);
				break;
			}
		}
		if(user.getStatut() == null){
			error = "Statut non conforme";
		}
		try {
			userRepository.save(user);
		} catch (Exception e) {
			e.printStackTrace();
			error = "Utilisateur déjà existant";
		}
	}
	
	public String getError(){
		return this.error;
	}

	@Override
	public User Consult(String fname, String  lname, String login, String mail) throws ServiceException {
		User user = userRepository.findByFnameAndLname(fname, lname);
		if (user.equals(null)){
			user = userRepository.findByLogin(login);
			if (user.equals(null)){
				user = userRepository.findByMail(mail);
				if (user.equals(null)){
					error = "Utilisateur inexistant";
				}
			}		
		}
		return null;
	}

	@Override
	public void Update(String fname, String lname, String mail) {
		// TODO Auto-generated method stub
		
	}

}
