package Test.config;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

@WebFilter("/*")
public class filter implements webFilter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException{
		
		//필터 초기화시 실행되는 메서드!
		
		System.out.println("Filter initialized name : " + filterConfig.getFilterName());
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		//요청전 처리작업
		System.out.println("Request intercepted in Filter");
		
		// 다음 필터 또는 최종 서블릿으로 전달 요청
		chain.doFilter(request, response);
		
		//응답 후 처리 작업
		System.out.println("Response intercepted Filter");
		
	}

	@Override
	public void destroy(){
		//종료작업
		System.out.println("Filter destroyed");
		
	}
	
	// 필요 메서드 추가 시: 
	// 1. 인터페이스에 메서드 선언 필요
	// 2. 구현 클래스에서 @Override 어노테이션 추가 필요
	public void filterMethod() {
		System.out.println("Filter method executed");
	}
	

	
}
