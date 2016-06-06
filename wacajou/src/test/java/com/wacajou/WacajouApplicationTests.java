package com.wacajou;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.wacajou.module.TestModuleCreate;
import com.wacajou.module.TestModuleUpdate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WacajouApplication.class)
@WebAppConfiguration
public class WacajouApplicationTests {
	
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void allTestModule(){
		new TestModuleCreate();
		new TestModuleUpdate();
	}
}
