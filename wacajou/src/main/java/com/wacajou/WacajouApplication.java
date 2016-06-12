package com.wacajou;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class WacajouApplication {
	
	
	private static final Logger logger = LoggerFactory
			.getLogger(SpringApplication.class);
	public static final String ROOT = "C:/Users/X-Strike/Documents/GitHub/Wacajou/wacajou/src/main/webapp/";
	
	public static void main(String[] args) {
		logger.info("Application started");
		SpringApplication.run(WacajouApplication.class, args);
	}	
}
