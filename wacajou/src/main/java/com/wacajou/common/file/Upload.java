package com.wacajou.common.file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.ServletContext;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.wacajou.WacajouApplication;
import com.wacajou.config.WebServletConfig;

public class Upload {

	private String error = null;

	public Upload() {

	}

	public String getError() {
		return this.error;
	}

	public String upload(MultipartFile file, String name,
			String[] autoFormat, ServletContext servletContext) {
		if ((!file.isEmpty()) && (file != null)) {
			String path;
			String filename = file.getOriginalFilename();
			String[] tmpFile = filename.split("\\.");
			String extension = tmpFile[tmpFile.length - 1].toLowerCase();
			boolean upload = false;
			for (int i = 0; i < autoFormat.length; i++)
				if (extension.equals(autoFormat[i]))
					upload = true;
			if (upload)
				try {
					path = "module_" + name + "." + extension;
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(new File(servletContext
									.getAttribute(WebServletConfig.ROOT_PATH)
									+ "/module/" + path)));
					FileCopyUtils.copy(file.getInputStream(), stream);
					stream.close();
					return path;
				} catch (Exception e) {
					error = "You failed to upload " + name + " => "
							+ e.getMessage();
					return null;
				}
			else
				error = "You failed to upload " + name
						+ " because the file extension was not supported.";
		} else
			error = "You failed to upload " + name
					+ " because the file was empty";
		return null;
	}
}
