package Test.config;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class interceptor implements webInterceptor {

	
    /**
     * 컨트롤러 실행 전 호출
     *
     * @param request  현재 요청 객체
     * @param response 현재 응답 객체
     * @param handler  요청을 처리할 핸들러 (컨트롤러)
     * @return true면 요청 계속 진행, false면 요청 중단
     */
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		
		
		System.out.println("Pre Handler: Controller 실행전");
		//요청을 계속 진행하려면 true, 중단하려면 false 반환
		return true;
		
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
							org.springframework.web.servlet.ModelAndView modeAndView) throws Exception {
		
		System.out.println("Post Handle: Controller 실행후");
		
	}
	
	// 모든처리가 완료된 후 호출
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object Handler, Exception ex) throws Exception {
		
		System.out.println("After completion : 응답 완료후");
		
	}
}
