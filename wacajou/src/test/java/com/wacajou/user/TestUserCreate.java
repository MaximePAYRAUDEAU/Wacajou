package com.wacajou.user;

import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.wacajou.WacajouApplication;
import com.wacajou.data.jpa.repository.UserRepository;
import com.wacajou.data.jpa.service.impl.UserServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WacajouApplication.class)
@WebAppConfiguration
public class TestUserCreate {
	@Autowired
	private UserRepository userRepository;
	

	@Test
	public void testUserServiceCreateAllEmpty(){
		UserServiceImpl service = new UserServiceImpl(userRepository);
		service.Create("", "", "");
		assertNull(service.getError());
	}
	
	@Test
	public void testUserServiceCreateLogin(){
		UserServiceImpl service = new UserServiceImpl(userRepository);
		service.Create("test0", "", "");
		assertNull(service.getError());
	}
	
	@Test
	public void testUserServiceCreateLoginAndPromo(){
		UserServiceImpl service = new UserServiceImpl(userRepository);
		service.Create("test1", "A1", "");
		assertNull(service.getError());
	}
	
	@Test
	public void testUserServiceCreateLoginAndPromoAndStatutFalse(){
		UserServiceImpl service = new UserServiceImpl(userRepository);
		service.Create("test2", "A1", "gfhd");
		assertNull(service.getError());
	}
	
	@Test
	public void testUserServiceCreateLoginAndPromoAndStatutTrue(){
		UserServiceImpl service = new UserServiceImpl(userRepository);
		service.Create("test3", "A1", "STUDENT");
		assertNull(service.getError());
	}
}
