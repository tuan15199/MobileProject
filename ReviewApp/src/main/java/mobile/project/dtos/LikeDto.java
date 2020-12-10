package mobile.project.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LikeDto {
	private Integer id;
	private Integer userId;
	private Integer commentId;
	
	public LikeDto(Integer id, Integer userId, Integer commentId) {
		super();
		this.id = id;
		this.userId = userId;
		this.commentId = commentId;
	}
	
}
