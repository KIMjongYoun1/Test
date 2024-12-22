package Test.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import Test.VO.Test2VO;
import Test.VO.TestVO;

@Mapper
public interface TestDAO {
	//유저 정보
	//사용자 정보 조회
	public TestVO getUserById(String userId);
	
	//새 사용자 등록
	public void insertUser(TestVO testVO);
// -----------------------------------
	
	// 게시글 정보
	
	// 게시글 작성
	public void insertPost(Test2VO test2VO);
	// 게시글 불러오기
	public Test2VO getPostById(int postId);
	// 게시글 전체 호출
	public List<Test2VO> getAllpost();
	
}

