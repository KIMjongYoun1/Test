package Test.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import Test.VO.Test2VO;
import Test.VO.Test3VO;
import Test.VO.TestVO;

@Mapper
public interface TestDAO {
	// 유저 정보
	// 사용자 정보 조회
	public TestVO getUserById(String userId);

	// 새 사용자 등록
	public void insertUser(TestVO testVO);
// -----------------------------------

	// 게시글 정보

	// 게시글 작성
	public void insertPost(Test2VO test2VO);

	// 게시글 불러오기
	public Test2VO getPostById(int postId);

	// 게시글 전체 호출
	public List<Test2VO> getAllpost();

	// 댓글 작성
	public void insertComent(Test3VO test3);

	// 댓글 조회
	public List<Test3VO> getByComentId(long postId);

	// 좋아요 추가
	// dao vo로 매핑하는 모든방법 동원했으나 마이베티스 매핑오류발생 명시적으로 param 해서 명식적으로 매개변수 전달해야마이베티스 오류안남
	// 그냥 Test4VO test 하면 찾지못한다는 오류만 지속 발생 , 매개변수가 2개이상이면 발생할수 있는 오류로 변수 param으로 명시해줘야함
	public  void insertLike(@Param("userId") String userId, @Param("postId") Long postId);

	// 좋아요 상태 확인
	public boolean checkLike(long postId, String userId);

	// 게시글 좋아요 수 조회
	public int getLikeCount(long postId);



}
