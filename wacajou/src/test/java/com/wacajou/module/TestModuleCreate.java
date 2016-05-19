package com.wacajou.module;

import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.wacajou.WacajouApplication;
import com.wacajou.data.jpa.repository.ModuleRepository;
import com.wacajou.data.jpa.service.impl.ModuleServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WacajouApplication.class)
@WebAppConfiguration
public class TestModuleCreate {

	@Autowired
	private ModuleRepository moduleRepository;
	
	@Test
	public void testModuleServiceCreateAllEmpty(){
		ModuleServiceImpl service = new ModuleServiceImpl(moduleRepository);
		service.Create("", "", "", "");
		assertNull(service.getError());
	}
	
	@Test
	public void testModuleServiceCreateName(){
		ModuleServiceImpl service = new ModuleServiceImpl(moduleRepository);
		service.Create("test0", "", "", "");
		assertNull(service.getError());
	}
	
	@Test
	public void testModuleServiceCreateNameAndDomainFalse(){
		ModuleServiceImpl service = new ModuleServiceImpl(moduleRepository);
		service.Create("test1", "", "", "Odsf");
		assertNull(service.getError());
	}
	
	@Test
	public void testModuleServiceCreateNameAndDomainTrue(){
		ModuleServiceImpl service = new ModuleServiceImpl(moduleRepository);
		service.Create("test2", "", "", "OTHER");
		assertNull(service.getError());
	}
	
	@Test
	public void testModuleServiceCreateNameAndDomainAndDescription(){
		ModuleServiceImpl service = new ModuleServiceImpl(moduleRepository);
		service.Create("test3", "testDesc3", "", "OTHER");
		assertNull(service.getError());
	}
	
	@Test
	public void testModuleServiceCreateNameAndDomainAndDescriptionAndImage(){
		ModuleServiceImpl service = new ModuleServiceImpl(moduleRepository);
		service.Create("test4", "testDesc4", "test123", "OTHER");
		assertNull(service.getError());
	}
	
	@Test
	public void testModuleServiceCreateNameAndDomainAndDescriptionAndImageSame(){
		ModuleServiceImpl service = new ModuleServiceImpl(moduleRepository);
		service.Create("test5", "testDesc4", "test123", "OTHER");
		service.Create("test5", "testDesc4", "test123", "OTHER");
		assertNull(service.getError());
	}
}
