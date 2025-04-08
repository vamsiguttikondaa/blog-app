package com.blogapp.payloads;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T>{
	private String message;
	private boolean success;
	private LocalDateTime timestamp;
    private T data;
    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
