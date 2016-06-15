package com.wacajou.config;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration()
@PropertySource("classpath:/config/install.properties")
public class WebServletConfig {
	private static final String INSTALL_PATH = "install.path";  
	public static final String ROOT_PATH = "root";
	public static final String VIEW = "number_consult";
	
	@Autowired
    Environment env;
	
	@Bean
	public ServletContext initServletContext(ServletContext context){
		context.setAttribute(ROOT_PATH, env.getProperty(INSTALL_PATH) + "/Wacajou");
		context.setAttribute(VIEW, 0);
		return context;
	}
}
