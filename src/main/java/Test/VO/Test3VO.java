package Test.VO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Test3VO {

	private Long comentId;
	private Long postId;
	private String content;
	private String authorId;
	private LocalDateTime createdAt;
	
}
