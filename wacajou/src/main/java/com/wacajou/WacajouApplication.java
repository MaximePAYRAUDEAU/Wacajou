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
	public static final String ROOT = "C:/Users/X-Strike/Documents/GitHub/Wacajou/wacajou/src/main/webapp/";
	
	public static void main(String[] args) {
		SpringApplication.run(WacajouApplication.class, args);
	}
}
