package mobile.project.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {
	private Integer commentId;
	private String content;
	private LocalDate postDate;
	private LocalTime postTime;
	private Integer userId;
	private Integer shopId;
	
	public CommentDto(Integer commentId, String content, LocalDate postDate, LocalTime postTime, Integer userId,
			Integer shopId) {
		super();
		this.commentId = commentId;
		this.content = content;
		this.postDate = postDate;
		this.postTime = postTime;
		this.userId = userId;
		this.shopId = shopId;
	}
	
}
