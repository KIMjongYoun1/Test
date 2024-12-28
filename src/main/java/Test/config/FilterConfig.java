package Test.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean<filter> filter(){
	
		FilterRegistrationBean<filter> registartionBean = new FilterRegistrationBean<>();
		registartionBean.setFilter(new filter());
		registartionBean.addUrlPatterns("/*"); // 요청 경로설정
		registartionBean.setOrder(1); // 필터실행순서 숫자가작을수록 먼저실행
		return registartionBean;
		
	}
	
	@Bean
	public FilterRegistrationBean<logInfilter> logInfilter(){
	
		FilterRegistrationBean<logInfilter> registartionBean = new FilterRegistrationBean<>();
		registartionBean.setFilter(new logInfilter());
		registartionBean.addUrlPatterns("/*"); // 요청 경로설정
		registartionBean.setOrder(2); // 필터실행순서 숫자가작을수록 먼저실행
		return registartionBean;
		
	}
	
}
