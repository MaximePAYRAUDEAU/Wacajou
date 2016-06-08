package com.wacajou;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.wacajou.data.jpa.domain.Statut;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.repository.UserRepository;

@SpringBootApplication
@EnableAutoConfiguration
public class WacajouApplication {
	private static final Logger logger = LoggerFactory
			.getLogger(SpringApplication.class);
	public static final String ROOT = "L:/";

	@PostConstruct
	public void logSomething() {
		logger.debug("Sample Debug Message");
		logger.trace("Sample Trace Message");
	}
	
	public static void main(String[] args) {
		SpringApplication.run(WacajouApplication.class, args);
	}
	
/*	@Bean 
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
	}*/
}
