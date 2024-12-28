package Test.config;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*") // /설정 경로에만 필터 적용
public class logInfilter implements webFilter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

		// 필터 초기화시 실행되는 메서드!

		System.out.println("loginfilter initialized");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		//요청전 처리작업
		System.out.println("Request intercepted in loginFilter");
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		  // 제외할 경로 설정
        String path = httpRequest.getRequestURI();
        if (path.equals("/test") || path.equals("/login")) {
            chain.doFilter(request, response); // 필터 건너뛰고 다음으로 전달
            return;
        }
		
		// 현재 요청 세션 가져오기
		HttpSession session = httpRequest.getSession(false);
		
		// 세션에서 로그인정보 가져오기
		Object user = (session != null ? session.getAttribute("loginuser") : null);
		
		//로그인 되지 않은경우
		if (user == null) {
			
			System.out.println("로그인이 필요합니다.");
			httpResponse.sendRedirect("/login");
			return;
		}
		
		// 로그인된경우
		System.out.println("로그인 사용자 : " + session.getAttribute("loginuser"));
		// 다음 필터 또는 최종 서블릿으로 전달 요청
		chain.doFilter(request, response);
		
		//응답 후 처리 작업
		System.out.println("Response intercepted Filter");
		
	}

	@Override
	public void destroy() {
		// 종료작업
		System.out.println("longinFilter destroyed");

	}

	// 필요 메서드 추가 시:
	// 1. 인터페이스에 메서드 선언 필요
	// 2. 구현 클래스에서 @Override 어노테이션 추가 필요
	public void filterMethod() {
		System.out.println("Filter method executed");
	}

}
