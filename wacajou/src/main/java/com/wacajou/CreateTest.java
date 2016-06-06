package com.wacajou;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wacajou.data.jpa.service.UserService;

@Configuration
public class CreateTest {

	@Autowired
	private UserService userService;
	
	@Bean
	public String setAdmin() {

		if (userService.getUserByLogin("admin") == null) {
			userService.Create("admin", "NONE", "ADMIN");
		}
		return "";
	}
}
