package com.blogapp.payloads;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

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
	@JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
        timestamp=LocalDateTime.now();
    }
}
