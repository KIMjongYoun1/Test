package Test.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import Test.DAO.TestDAO;
import Test.Service.TestService;
import Test.Util.fileUpload;
import Test.VO.Test2VO;
import Test.VO.Test3VO;
import Test.VO.TestVO;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class TestController {
	// -------------------유저 매서드
	@Autowired
	private TestService testService;
	@Autowired
	private TestDAO testDAO;

	@GetMapping("/test")
	public String homePage(HttpSession session, Model model) {
		TestVO loginuser = (TestVO) session.getAttribute("loginuser");

		if (loginuser != null) {
			model.addAttribute("user", loginuser);
		} else {
			model.addAttribute("message", "로그인되지 않은 상태입니다.");
		}
		List<Test2VO> posts = testService.getAllpost();
		model.addAttribute("posts", posts);

		return "/test";

	}

	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}

	@PostMapping("/signup")
	public String insertUser(@ModelAttribute TestVO testVO, Model Model) {
		try {
			testService.insertUser(testVO);
		} catch (Exception e) {
			log.info("회원가입 중 오류 발생: {}", e.getMessage(), e);

			return "/signup";
		}
		return "/login";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/login")
	public String login2(@RequestParam String userId, @RequestParam String passWord, HttpSession session) {

		if (session.getAttribute("loginuser") != null) {
			System.out.println("이미 로그인된 상태입니다.");
			return "redirect:/test"; // 이미 로그인된 경우 테스트 페이지로 이동
		}

		TestVO user = testDAO.getUserById(userId);

		if (user == null) {
			System.out.println("사용자를 찾을 수 없습니다: userId = " + userId);
			return "login";
		}
		if (!user.getPassWord().equals(passWord)) {
			System.out.println("비밀번호가 일치하지 않습니다.");
			return "login";
		}

		session.setAttribute("loginuser", user);
		System.out.println("로그인 성공: " + userId);

		return "redirect:/test";
	}

	@PostMapping("/logout")
	public String logout(HttpSession session) {
		if (session != null) {
			session.invalidate();
			System.out.println("세션이 무효화 되었습니다.");
		}
		return "redirect:/test";
	}
	// -------------------------- 게시글매서드

	@GetMapping("/post")
	public String createPost(HttpSession session) {
		// 상단 test에서 구현된 세션 사용
		if (session.getAttribute("loginuser") == null) {
			System.out.println("로그인이 필요합니다");
			return "redirect:login"; // 이미 로그인된 경우 테스트 페이지로 이동
		}

		return "/post";
	}

	@PostMapping("/post")
	public String createPost2(@ModelAttribute Test2VO test2, HttpSession session, @RequestParam MultipartFile file,
			Model model) throws IOException {
		TestVO user = (TestVO) session.getAttribute("loginuser");
		if (user == null) {
			return "redirect:/login";
		}

		// 작성자 정보 설정
		String userName = user.getUserName();
		test2.setAuthorId(userName);

		// 파일 업로드 로직
		List<String> allowedExtensions = List.of("jpg", "jpeg", "png", "gif");
		long maxSize = 10 * 1024 * 1024;

		if (!fileUpload.inValidFile(file, allowedExtensions, maxSize)) {
			model.addAttribute("error", "허용되지 않은 파일입니다.");
			return "post";
		}

		String filePath = fileUpload.uploadFile(file);
		test2.setThumbnail(filePath);

		testService.savePost(test2);

		return "redirect:/list";
	}

	@GetMapping("/list")
	public String listPosts(Model model) {
		List<Test2VO> posts = testService.getAllpost();
		model.addAttribute("posts", posts);
		return "list"; // 게시글 목록 페이지 (list.html) 반환
	}

	@GetMapping("/post/{id}")
	public String viewPost(@PathVariable("id") int postId, Model model) {
		Test2VO post = testService.getPostById(postId);

		if (post == null) {
			log.info("포스트가 업습니다.");
			return "redirect:/list";
		}
		log.info("게시글내용 : " + post.getContent());
		
		List<Test3VO> coment = testService.getComent(postId);
		
		model.addAttribute("post", post);
		model.addAttribute("coment", coment);
		return "postDetail";
	}

	// 댓글작성 매서드
	@PostMapping("/coment")
	public String addComnet(@RequestParam long postId, @RequestParam String content, HttpSession session) {
		TestVO user = (TestVO) session.getAttribute("loginuser");

		if (user == null) {
			return "redirect:/login";

		}

		String authorId = user.getUserId();
		Test3VO test3 = new Test3VO();
		test3.setPostId(postId);
		test3.setContent(content);
		test3.setAuthorId(authorId); // 작성자 id 세팅

		testService.addComent(test3);

		return "redirect:/post/" + postId; // 게시글 상세페이

	}

	// 댓글 조회 매서드
	@GetMapping("/post/{id}/coment")
	public String getComent(@PathVariable("id") long postId, Model model) {
		// 댓글조회 목록 리스트
		List<Test3VO> coment = testService.getComent(postId);
		model.addAttribute("coment", coment);
		return "postDetail";

	}
}
