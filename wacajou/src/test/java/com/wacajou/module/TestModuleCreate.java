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
public class TestModuleCreate {

	@Autowired
	private ModuleService service;
	
	@Test
	public void testModuleServiceCreateAllEmpty(){
		service.Create("", "", "", "", null);
		assertNull(service.getError());
	}
	
	@Test
	public void testModuleServiceCreateName(){
		service.Create("test0", "", "", "", null);
		assertNull(service.getError());
	}
	
	@Test
	public void testModuleServiceCreateNameAndDomainFalse(){
		service.Create("test1", "", "", "Odsf", null);
		assertNull(service.getError());
	}
	
	@Test
	public void testModuleServiceCreateNameAndDomainTrue(){
		service.Create("test2", "", "", "OTHER", null);
		assertNull(service.getError());
	}
	
	@Test
	public void testModuleServiceCreateNameAndDomainAndDescription(){
		service.Create("test3", "testDesc3", "", "OTHER", null);
		assertNull(service.getError());
	}
	
	@Test
	public void testModuleServiceCreateNameAndDomainAndDescriptionAndImage(){
		service.Create("test4", "testDesc4", "test123", "OTHER", null);
		assertNull(service.getError());
	}
	
	@Test
	public void testModuleServiceCreateNameAndDomainAndDescriptionAndImageSame(){
		service.Create("test5", "testDesc4", "test123", "OTHER", null);
		service.Create("test5", "testDesc4", "test123", "OTHER", null);
		assertNull(service.getError());
	}
}
