package com.wacajou;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

import com.wacajou.init.CreateDb;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		new CreateDb();
		
		return application.sources(WacajouApplication.class);
	}
}
