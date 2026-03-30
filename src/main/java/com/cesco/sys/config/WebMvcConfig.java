package com.cesco.sys.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	
	@Autowired
	private LoginIntereptor interceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor)
			.addPathPatterns(new String[] {"/**"})
			.excludePathPatterns(new String[] {
				 "/user/**"
				,"/api/**"
				,"/jqwidgets/**"
				,"/css/**"
				,"/js/**"
				,"/images/**"
				,"/error"});
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
//		registry.addResourceHandler("/img/**").addResourceLocations("C:/svc/act17/web/file/").setCachePeriod(0);
//		registry.addResourceHandler("/img/**").addResourceLocations(FILE_DIR).setCachePeriod(0);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
	registry.addMapping("/**")
		.allowedOrigins("http://localhost:8080", "http://localhost:3000") //허용할 출처
		.allowedMethods("GET", "POST","PUT","DELETE") // 허용할 HTTP method
		.allowCredentials(true) // 쿠키 인증 요청 허용
		.maxAge(3000); // 원하는 시간만큼 pre-flight 리퀘스트를 캐싱
	}
}
