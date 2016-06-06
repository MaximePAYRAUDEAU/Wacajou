package com.wacajou;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
/**
 * 
 * <h1>Grant location to /WEB-INF/ for HTML / JSP pages
 * 
 * @author Payraudeau Maxime
 *	@version 1.0 22/05/2016
 */
@Configuration
@ComponentScan("com.wacajou")
@EnableWebMvc
@EnableTransactionManagement
public class Resolver {
	
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setSuffix(".html");
		return viewResolver;
	}
	
	@Bean
	public ServletContextTemplateResolver templateResolver() {
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setCacheable(false);
		templateResolver.setPrefix("/WEB-INF/");
		templateResolver.setSuffix(".jsp");
		templateResolver.setSuffix(".html");
		return templateResolver;
	}
	
	@Bean 
	public SpringTemplateEngine templateEngine(){
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.addTemplateResolver(templateResolver());
		return templateEngine;
		
	}
	
	@Bean
	public ContentNegotiatingViewResolver negotiattingViewResolver(){
		ContentNegotiatingViewResolver negotiattingViewResolver = new ContentNegotiatingViewResolver();
		List<ViewResolver> viewResolver = new ArrayList<ViewResolver>();
		viewResolver.add(thymeleafViewResolver());
		viewResolver.add(beanNameViewResolver());
		negotiattingViewResolver.setViewResolvers(viewResolver);
		return negotiattingViewResolver;
		
	}
	
	@Bean
	public BeanNameViewResolver beanNameViewResolver(){
		BeanNameViewResolver beanNameViewResolver = new BeanNameViewResolver();
		beanNameViewResolver.setOrder(1);
		return beanNameViewResolver;
	}
	
	@Bean
	public ThymeleafViewResolver thymeleafViewResolver(){
		ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
		thymeleafViewResolver.setTemplateEngine(templateEngine());
		thymeleafViewResolver.setOrder(2);
		return thymeleafViewResolver;
	}
}
