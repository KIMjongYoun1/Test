package Test.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Test.DAO.TestDAO;
import Test.VO.Test2VO;
import Test.VO.TestVO;

@Service
public class TestService {

	@Autowired
	private TestDAO testDAO;

	// 사용자 등록처리
	public void insertUser(TestVO test) {
		testDAO.insertUser(test);
	}

	// 유저정보 조회
	public TestVO selectUser(String userId) {
		return testDAO.getUserById(userId);
	}

	// 게시글저장
	public void savePost(Test2VO post) {

		testDAO.insertPost(post);
	}
	
	public Test2VO getPostById(int postId) {
		return testDAO.getPostById(postId);
	}

	// 게시글 목록 조회
	public List<Test2VO> getAllpost() {
		return testDAO.getAllpost();
	}
}
