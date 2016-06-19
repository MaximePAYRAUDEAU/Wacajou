package com.wacajou.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.wacajou.data.jpa.domain.User;

public class LoginInterceptor implements HandlerInterceptor  {
	private static final Logger logger = LoggerFactory
			.getLogger(LoginInterceptor.class);
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex) throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		if(user.isConnect()){
			logger.info("User " + user.getLogin() + " connected !");
			System.out.println("User " + user.getLogin() + " connected !");
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}
} 