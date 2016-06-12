package com.wacajou.controller.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wacajou.config.WebServletConfig;

@Controller
@PropertySource("classpath:/config/install.properties")
public class ImageController {
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	static Environment env;
	
	@RequestMapping(value = "/file/{folder}/{name}.{extension}", method = RequestMethod.GET)
	@ResponseBody
	public byte[] getImageAsByteArray(
			@PathVariable(value = "name") String name,
			@PathVariable(value = "folder") String folder,
			@PathVariable(value = "extension") String extension,
			HttpServletResponse response) throws IOException {
		if (extension != null) {
			File file = new File(servletContext.getAttribute(WebServletConfig.ROOT_PATH) + folder + "/"
					+ name + "." + extension);
			FileInputStream fis = null;
	//		InputStream in = null;
			byte[] fileReturn = null;
			try {
				fis = new FileInputStream(file);

				fileReturn = IOUtils.toByteArray(fis);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fis != null)
						fis.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			return fileReturn;
		} else
			return null;
	}
}
