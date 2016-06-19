package com.wacajou.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.wacajou.handler.AdminInterceptor;
import com.wacajou.handler.LoggingInterceptor;
import com.wacajou.handler.LoginInterceptor;
import com.wacajou.handler.UserInterceptor;
/**
 * <h1>Configure Spring MVC Frameworks
 * 
 * @author Payraudeau Maxime
 *
 */
@Configuration()
@PropertySource("classpath:/config/install.properties")
@EnableWebMvc
public class WebAppConfig extends WebMvcConfigurerAdapter {
	@Autowired
    Environment env;
	
	public static final String HOME_PAGE = "app.home";
	
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		super.addResourceHandlers(registry);
		registry.addResourceHandler("/images/**").addResourceLocations(
				"/images/");
		registry.addResourceHandler("/css/**").addResourceLocations("/css/");
		registry.addResourceHandler("/js/**").addResourceLocations("/js/");
		registry.addResourceHandler("/fonts/**").addResourceLocations("/fonts/");
		//registry.addResourceHandler("/user/**").addResourceLocations("/user/");

	}
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
	    registry.addInterceptor(new LoggingInterceptor());
	    registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/login", "/login/test");
	    registry.addInterceptor(new UserInterceptor()).addPathPatterns("/profil/**", "/parcours/inscription/**", "/user/**");
	    registry.addInterceptor(new AdminInterceptor(env.getProperty(HOME_PAGE))).addPathPatterns("/administration", "/administration/**");
	} 
}
