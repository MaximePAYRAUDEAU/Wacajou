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

// @Configuration
public class CreateTest {
	private static final Logger logger = LoggerFactory
			.getLogger(CreateTest.class);
	@Autowired
	private UserService userService;
	
	@Bean
	public String setAdmin() {
		if (userService.getUserByLogin("admin") == null) {
			userService.Create("admin", "NONE", "ADMIN");
		}
		return "";
	}
	
	@Bean 
	public int createTestingUsers(UserRepository userRepository){
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
		logger.info("Users de test crées.");
		return 0;
	}
	
	@Bean 
	public int createTestingModule(ModuleRepository moduleRepository){
		Module module1 = new Module();
		module1.Create("test_1", "test_description_1", null, Domain.ELECTRONIC);
		Module module2 = new Module();
		module2.Create("test_2", "test_description_2", null, Domain.INFORMATIC);
		Module module3 = new Module();
		module3.Create("test_3", "test_description_3", null, Domain.LANGUES);
		Module module4 = new Module();
		module4.Create("test_4", "test_description_4", null, Domain.MANAGERIAL);
		Module module5 = new Module();
		module5.Create("test_5", "test_description_5", null, Domain.OTHER);
		Module module6 = new Module();
		module6.Create("test_6", "test_description_6", null, Domain.ELECTRONIC);
		Module module7 = new Module();
		module7.Create("test_7", "test_description_7", null, Domain.INFORMATIC);
		Module module8 = new Module();
		module8.Create("test_8", "test_description_8", null, Domain.LANGUES);
		
		moduleRepository.save(module1);
		moduleRepository.save(module2);
		moduleRepository.save(module3);
		moduleRepository.save(module4);
		moduleRepository.save(module5);
		moduleRepository.save(module6);
		moduleRepository.save(module7);
		moduleRepository.save(module8);
		
		logger.info("Module de test crées.");
		return 0;
	}
}
