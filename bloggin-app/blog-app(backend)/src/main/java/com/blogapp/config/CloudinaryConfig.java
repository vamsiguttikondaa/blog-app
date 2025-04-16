package com.blogapp.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {
	@Value("${cloudinary.cloud_name}")
	private String cloudName;

    @Value("${cloudinary.api_key}")
    private String apiKey;

    @Value("${cloudinary.api_secret}")
    private String apiSecret;
    
    
    @Bean
    public Cloudinary getCloudinary() {
    	Map<String, String> map=new HashMap<>();
    	map.put("api_key", apiKey);
    	map.put("api_secret", apiSecret);
    	map.put("cloud_name", cloudName);
    	Cloudinary cloudinary=new Cloudinary(map);
    	return cloudinary;
    	
    }

}
