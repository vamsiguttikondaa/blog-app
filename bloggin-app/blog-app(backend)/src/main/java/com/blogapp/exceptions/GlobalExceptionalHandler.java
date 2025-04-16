package com.blogapp.exceptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionalHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public  ResponseEntity<Map<String,Object>>  handleResourceNotFoundException(ResourceNotFoundException re) {
		Map<String,Object> map=new HashMap<>();
		map.put("message:", re.getMessage());
		map.put("status:",HttpStatus.NOT_FOUND.value() );
		return new ResponseEntity<>(map,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException  ex){
		Map<String,String> errormap=new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach((error)->{
			errormap.put(error.getField(), error.getDefaultMessage());
		});
		return new ResponseEntity<>(errormap, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(IOException.class)
	public ResponseEntity<Map<String, String>> handleIoException(IOException ex) {
	    Map<String, String> errormap = new HashMap<>();
	    errormap.put("error", "I/O error occurred: " + ex.getMessage());
	    return new ResponseEntity<>(errormap, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
