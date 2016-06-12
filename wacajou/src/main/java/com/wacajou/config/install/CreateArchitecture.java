package com.wacajou.config.install;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration()
@Profile("install")
@PropertySource("classpath:/config/install.properties")
public class CreateArchitecture {
	
	private static final String INSTALL_PATH = "install.path";  
	
	private static final Logger logger = LoggerFactory
			.getLogger(CreateArchitecture.class);
	
	@Autowired
    Environment env;
	
	@Bean
	public boolean InstallArchitecture(){
		logger.info("Initialisation 'CreateArchitecture' started" );
		String path = env.getRequiredProperty(INSTALL_PATH);
		logger.info("Install path set as : " + path);
		try {
			createArchitecture(path);
		} catch (Exception e1) {
			logger.error("Error creating architecture : " + e1.getMessage());
		}
		ConfigFile cf = new ConfigFile();
		cf.getFile(path);
		if(cf.getPath() == null){
			cf.InstallConfigFile(path);
		}
		try {
			createArchitecture(cf.getPath());
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return true;
	}
	
	public void createArchitecture(String rootPath) throws Exception {
		logger.info("Create architecture of the software");
		if(!rootPath.equals("")){		
			CreateFolder cFolder = new CreateFolder(rootPath);
			cFolder.createRoot("Wacajou");
			cFolder.createOne("uploads");
			cFolder.createOne("module");
			cFolder.createOne("parcours");
			cFolder.createOne("user");
			cFolder.createOne("config");
			logger.info("All folder are created");
		}else
			throw new Exception("Architecture not initialize");
	}
}