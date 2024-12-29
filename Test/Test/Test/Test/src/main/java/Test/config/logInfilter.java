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
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	        throws IOException, ServletException {

	    System.out.println("Request intercepted in loginFilter");

	    HttpServletRequest httpRequest = (HttpServletRequest) request;
	    HttpServletResponse httpResponse = (HttpServletResponse) response;

	    // 제외할 경로 설정
	    String path = httpRequest.getRequestURI();
	    System.out.println("Filter: Current path = " + path);

	    if (path.startsWith("/login") || path.startsWith("/signup") || path.startsWith("/test")) {
	        chain.doFilter(request, response); // 필터 건너뛰고 다음으로 전달
	        return;
	    }

	    // 현재 요청 세션 가져오기
	    HttpSession session = httpRequest.getSession(false);
	    Object user = (session != null ? session.getAttribute("loginuser") : null);

	    // 세션 상태 로그 확인
	    System.out.println("Filter: Current session = " + session);
	    System.out.println("Filter: Login user = " + (user != null ? user.toString() : "null"));

	    if (user == null) {
	        System.out.println("로그인이 필요합니다. Redirecting to /login");
	        httpResponse.sendRedirect("/login");
	        return;
	    }

	    System.out.println("로그인 사용자 : " + user);

	    chain.doFilter(request, response); // 다음 필터 또는 컨트롤러로 전달
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
