package com.tonnybunny.config;


import com.tonnybunny.interceptor.LoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

	private final LoginInterceptor loginInterceptor;


	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor)
			.addPathPatterns("/**")             // 모든 url 에 대해 적용
			.excludePathPatterns("/login/**")   // 제외 1
			.excludePathPatterns("/signin/**")  // 제외 2
			.excludePathPatterns("/refresh/**") // 제외 3

			//회원가입
			.excludePathPatterns("/signup/**")  // 제외 
			.excludePathPatterns("/account/signup/**") // 제외 
			.excludePathPatterns("/send/authcode/**") // 제외
			.excludePathPatterns("/check/authcode/**") // 제외

			//커뮤니티
			.excludePathPatterns("/board/**") // 제외

			.excludePathPatterns("/swagger-resources/**")   // swagger
			.excludePathPatterns("/swagger-ui/**")          // swagger
			.excludePathPatterns("/v2/**");  // swagger
	}

}
