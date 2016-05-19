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
public class TestModuleConsult {
	@Autowired
	private ModuleRepository moduleRepository;
	
	@Test
	public void testModuleServiceConsult(){
		ModuleServiceImpl service = new ModuleServiceImpl(moduleRepository);
		service.Create("Update", "", "", "OTHER");
		service.Consult("Update");
		assertNull(service.getError());
	}
}
