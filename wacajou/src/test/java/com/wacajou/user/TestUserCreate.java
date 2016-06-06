package com.wacajou.user;

import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.wacajou.WacajouApplication;
import com.wacajou.data.jpa.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WacajouApplication.class)
@WebAppConfiguration
public class TestUserCreate {
	@Autowired
	private UserService service;

	@Test
	public void testUserServiceCreateAllEmpty() {
		service.Create("", "", "");
		assertNull(service.getError());
	}

	@Test
	public void testUserServiceCreateLogin() {
		service.Create("test0", "", "");
		assertNull(service.getError());
	}

	@Test
	public void testUserServiceCreateLoginAndPromo() {
		service.Create("test1", "A1", "");
		assertNull(service.getError());
	}

	@Test
	public void testUserServiceCreateLoginAndPromoAndStatutFalse() {
		service.Create("test2", "A1", "gfhd");
		assertNull(service.getError());
	}

	@Test
	public void testUserServiceCreateLoginAndPromoAndStatutTrue() {
		service.Create("test3", "A1", "STUDENT");
		assertNull(service.getError());
	}
	
}
