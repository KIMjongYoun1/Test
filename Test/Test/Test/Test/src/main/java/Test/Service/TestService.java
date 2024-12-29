package Test.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Test.DAO.TestDAO;
import Test.Util.AESCryptoService;
import Test.VO.Test2VO;
import Test.VO.Test3VO;
import Test.VO.Test4VO;
import Test.VO.TestVO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TestService {

	@Autowired
	private TestDAO testDAO;

	private final AESCryptoService cryptoService = new AESCryptoService();

	// 사용자 등록처리
	public void insertUser(TestVO test) {
		log.info("Original password for userId {}: {}", test.getUserId(), test.getPassWord());
		// 비밀번호 암호화
		String encryptedPassword = cryptoService.encrypt(test.getPassWord());
		
		log.info("Encrypted password for userId {}: {}", test.getUserId(), encryptedPassword);

		test.setPassWord(encryptedPassword);
		testDAO.insertUser(test);
		
		log.info("User {} successfully inserted with encrypted password.", test.getUserId());
	}

	// 유저정보 조회
	public TestVO selectUser(String userId) {
		TestVO user = testDAO.getUserById(userId);

		if (user != null) {
			String decryptedPassword = cryptoService.decrypt(user.getPassWord());
			user.setPassWord(decryptedPassword);
		}

		return user;
	}

	// 게시글저장
	public void savePost(Test2VO post) {

		testDAO.insertPost(post);
	}

	// 게시글 조회
	public Test2VO getPostById(int postId) {
		return testDAO.getPostById(postId);
	}

	// 게시글 목록 조회
	public List<Test2VO> getAllpost() {
		return testDAO.getAllpost();
	}

	// 댓글작성
	public void addComent(Test3VO test3) {
		testDAO.insertComent(test3);
	}

	// 댓글조회
	public List<Test3VO> getComent(long postId) {
		return testDAO.getByComentId(postId);
	}

	// 좋아요 추가
	public void insertLike(Test4VO test4) {
		String userId = test4.getUserId();
		Long postId = test4.getPostId();
		testDAO.insertLike(userId, postId); // DAO 호출
	}

	// 좋아요 상태조회
	public boolean checkLike(long postId, String userId) {

		if (userId == null) {
			return false;
		}

		return testDAO.checkLike(postId, userId);
	}

	// 좋아요 갯수조회
	public int getLikeCount(long postId) {

		return testDAO.getLikeCount(postId);
	}

}
