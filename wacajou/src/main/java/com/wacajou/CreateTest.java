package com.wacajou;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wacajou.data.jpa.domain.Domain;
import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.Statut;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.repository.ModuleRepository;
import com.wacajou.data.jpa.repository.UserRepository;
import com.wacajou.data.jpa.service.UserService;

//@Configuration
public class CreateTest {
	private static final Logger logger = LoggerFactory
			.getLogger(CreateTest.class);
	
	@Bean 
	public String createTestingUsers(UserRepository userRepository){
		User userAdmin = new User();
		userAdmin.Create("admin", "NONE", Statut.ADMIN);
		User userStudent = new User();
		userStudent.Create("test_user_student", "none", Statut.STUDENT);
		User userRespoModule  = new User();
		userRespoModule.Create("test_user_respo_module", "none", Statut.RESPO_MODULE);
		User userRespoParcours = new User();
		userRespoParcours.Create("test_user_respo_parcours", "none", Statut.RESPO_PARCOURS);
		User userRespoPeda = new User();
		userRespoPeda.Create("test_user_respo_peda", "none", Statut.RESPO_PEDAGOGIQUE);
		
		userRepository.save(userStudent);
		userRepository.save(userRespoModule);
		userRepository.save(userRespoParcours);
		userRepository.save(userRespoPeda);
		userRepository.save(userAdmin);
		logger.info("Users de test crées.");
		return "";
	}
}

