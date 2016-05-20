package com.wacajou.parcours;

import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.wacajou.WacajouApplication;
import com.wacajou.data.jpa.repository.ParcoursRepository;
import com.wacajou.data.jpa.repository.ModuleRepository;
import com.wacajou.data.jpa.repository.ParcoursModuleRepository;
import com.wacajou.data.jpa.service.impl.ParcoursServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WacajouApplication.class)

@WebAppConfiguration
public class TestParcoursCreate {
	
	@Autowired
	private ParcoursRepository parcoursRepository;
	
	@Autowired
	private ModuleRepository moduleRepository;
	
	@Autowired
	private ParcoursModuleRepository parcoursModuleRepository;
	
	@Test
	public void testParcoursServiceEmpty(){
		ParcoursServiceImpl service = new ParcoursServiceImpl(parcoursRepository, moduleRepository, parcoursModuleRepository);
		service.Create("");
		assertNull(service.getError());
	}
	
	@Test
	public void testParcoursServiceName(){
		ParcoursServiceImpl service = new ParcoursServiceImpl(parcoursRepository, moduleRepository, parcoursModuleRepository);
		service.Create("test0");
		assertNull(service.getError());
	}
	
}
