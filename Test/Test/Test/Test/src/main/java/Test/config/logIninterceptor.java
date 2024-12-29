package Test.config;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class logIninterceptor implements webInterceptor {

	
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
		
		 // 세션 가져오기 (세션이 없으면 null 반환)
		HttpSession session = request.getSession(false);
		
		 // 세션에서 로그인 사용자 정보 가져오기
		Object user = (session != null ? session.getAttribute("loginuser") : null);
		
		 // 로그인되지 않은 경우 처리
		if(user == null) {
			
			System.out.println("로그인이 필요합니다.");
			response.sendRedirect("/login");
			return false;
			
		}
		
		// 로그인된 사용자 확인
		System.out.println("로그인 사용자 : " + session.getAttribute("loginuser"));
		return true;
		
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
							org.springframework.web.servlet.ModelAndView modelAndView) throws Exception {
		
		System.out.println("Post Handle: Controller 실행후");
		
		if(modelAndView != null) {
			// 모델에 추가 데이터 넣기 테스트 코드 (특별한로직이 없음으로 예제코드 추가)
			
			modelAndView.addObject("DataInsert", "Test Data In");
		}
		System.out.println("PostHandler: 데이터 추가 완료");
		
	}
	
	// 모든처리가 완료된 후 호출
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object Handler, Exception ex) throws Exception {
		
		if (ex != null) {
			System.out.println("AfterCompletion : 예외 - " + ex.getMessage());
		}
		
		System.out.println("After completion : ----------------------------");
		
	}
}
