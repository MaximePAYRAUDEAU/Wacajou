package com.wacajou.file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.wacajou.WacajouApplication;

public class Upload {

	public Upload(){
		
	}
	
	public String upload(MultipartFile file, String path, String file_name){
		try {
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File(path + file_name)));
            FileCopyUtils.copy(file.getInputStream(), stream);
			stream.close();
			return "You successfully uploaded " + file_name + "!";
		}
		catch (Exception e) {
			return "You failed to upload " + file_name + " => " + e.getMessage();
		}
	}
}
