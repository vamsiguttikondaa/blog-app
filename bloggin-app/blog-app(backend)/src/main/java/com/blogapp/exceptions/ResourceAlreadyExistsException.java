package com.blogapp.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException {

	public ResourceAlreadyExistsException(String resourceName,Object fieldValue) {
		super(String.format("%s already exists :%s ",resourceName,fieldValue));
	}

}
