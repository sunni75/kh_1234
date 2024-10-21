package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class WebMVCConfig implements WebMvcConfigurer {
	
	private SessionInterceptor sessionInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// /admin/** 과 같은 패턴으로 인증이 필요한 URL을 입력한다.
		// 체크할 URL 패턴이 여러개일 경우 쉼표로 구분해서 넣는다.
		// 예) "admin/**", "member/**"
		registry.addInterceptor(sessionInterceptor)
				.addPathPatterns(
							"/admin/**", "/board/write"
						);
	}
	
	
	
}
