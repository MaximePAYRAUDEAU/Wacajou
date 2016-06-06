package com.wacajou.module;

import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.wacajou.WacajouApplication;
import com.wacajou.data.jpa.service.ModuleService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WacajouApplication.class)
@WebAppConfiguration
public class TestModuleUpdate {

	@Autowired
	private ModuleService service;
	
	@Test
	public void testModuleServiceUpdateFalse(){
		this.service.Update("tes0000");
		assertNull(this.service.getError());
	}

	@Test
	public void testModuleServiceUpdateTrue(){
		this.service.Create("Update", "", "", "OTHER", null);
		System.out.println(this.service.getError());
		this.service.Update("Update");
		assertNull(this.service.getError());
	}
	@Test
	public void testModuleServiceCreate(){
		this.service.Create("Update", "", "", "OTHER", null);
		assertNull(this.service.getError());
	}
}
