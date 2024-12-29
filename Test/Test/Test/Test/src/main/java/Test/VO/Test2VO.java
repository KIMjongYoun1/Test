package Test.VO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Test2VO {
	// 게시글 정보
	
    private int postId;
    private String title;
    private String content;
    private String authorId;
    private LocalDateTime createdAt;
    
    private String thumbnail; // 썸네일 파일 경로 새로생성 vo 
    
}
