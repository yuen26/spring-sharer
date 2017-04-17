package com.yuen.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

	public static File convert(MultipartFile multipartFile) {
		File file = new File(multipartFile.getOriginalFilename());
		try {
		    file.createNewFile(); 
		    FileOutputStream fos = new FileOutputStream(file); 
		    fos.write(multipartFile.getBytes());
		    fos.close(); 
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		} 
		return file;
	}
	
}
