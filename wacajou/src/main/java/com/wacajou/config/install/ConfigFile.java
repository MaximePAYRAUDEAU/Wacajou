package com.wacajou.config.install;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Scanner;


public class ConfigFile {
   
	private String path = null;
	private Scanner scanner;
	
	public ConfigFile(){
		
	}
	
	public void InstallConfigFile(String pathFile) {
		Properties prop = new Properties();
		OutputStream output = null;

		try {

			output = new FileOutputStream(pathFile + "config.properties");

			// set the properties value
			System.out.println("Chemin d'installation des fichiers ?");
			scanner = new Scanner(System.in);
			String path = scanner.next();
			scanner.close();
			prop.setProperty("PATH", path);
			this.path = path;
			
			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	public void getFile(String pathFile) {
		Properties prop = new Properties();
		InputStream input = null;

		try {

			String filename = "config.properties";
			input = new FileInputStream(pathFile + "config.properties");
			if (input == null) {
				System.out.println("Sorry, unable to find " + filename);
				return;
			}

			// load a properties file from class path, inside static method
			prop.load(input);

			// get the property value and print it out
			System.out.println(path = prop.getProperty("PATH"));

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getPath() {
		// TODO Auto-generated method stub
		return path;
	}

}