package com.blogapp.services;



import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;

@Service
public class FileUploadService {

	@Autowired
	private Cloudinary cloudinary;
	
	public String uploadImage(MultipartFile file) throws IOException
	{
	Map<?, ?> result=	cloudinary.uploader().upload(file.getBytes(),Map.of("folder","blogapp/posts"));
		return result.get("secure_url").toString();
		
	}
}
