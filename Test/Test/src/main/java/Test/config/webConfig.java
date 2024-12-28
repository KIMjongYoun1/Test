package Test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class webConfig implements WebMvcConfigurer {

	@Autowired
	private logIninterceptor logininterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(logininterceptor) // logininterceptor 등록
							.addPathPatterns("/*") // 인터셉터가 /login 경로에서 동작
							.excludePathPatterns("/login","/signup", "/test"); // 제외 경로 설정
	}
	
	
}
