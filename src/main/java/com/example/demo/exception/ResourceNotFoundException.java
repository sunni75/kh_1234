package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.Setter;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
	
	private String resourceName; // 데이터 베이스 어디에
	private String fieldName; // 무슨 컬럼명 중에
	private Object fieldValue; // 무슨 값
	
	public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
		super(String.format("%s에 해당하는 리소스를 찾을 수 없습니다. %s : '%s'", resourceName, fieldName, fieldValue));
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}
	
}
