package com.wacajou.config.install;

import java.io.File;

public class CreateFolder {
	private String path;
	private String root;
	private String parent;

	public CreateFolder(String PATH) {
		this.path = PATH;
	}
	
	public void createRoot(String folderName) {
		File dir = new File(path  + folderName);
		if (!dir.exists()) {
			boolean isDirectoryCreated = dir.mkdir();

			if (isDirectoryCreated)
				System.out.println("RootDirectory " + folderName + " created successfully");
			else
				System.out.println("RootDirectory was not created successfully");
		} else
			System.out.println("RootDirectory " + folderName + " already exist");

		root = folderName;
	}

	public void createOne(String folderName) {
		File dir = new File(path + root + "\\" + folderName);
		if (!dir.exists()) {
			boolean isDirectoryCreated = dir.mkdir();

			if (isDirectoryCreated)
				System.out.println("Directory " + folderName + " created successfully");
			else
				System.out.println("Directory was not created successfully");
		} else
			System.out.println("Directory " + folderName + " already exist");

		parent = folderName;
	}

	public void createChild(String folderName) {
		File dir = new File(path + root + "\\" + parent + "\\" + folderName);
		if (!dir.exists()) {
			boolean isDirectoryCreated = dir.mkdir();

			if (isDirectoryCreated)
				System.out.println("ChildDirectory " + parent + "\\" + folderName + " created successfully");
			else
				System.out.println("ChildDirectory was not created successfully");
		} else
			System.out.println("ChildDirectory "  + parent + "\\" + folderName + " already exist");
	}
}