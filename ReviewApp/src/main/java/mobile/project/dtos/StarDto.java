package mobile.project.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StarDto {
	private Integer id;
	private int starNumber;
	private Integer userId;
	private Integer shopId;
	
	public StarDto(Integer id, int starNumber, Integer userId, Integer shopId) {
		this.id = id;
		this.starNumber = starNumber;
		this.userId = userId;
		this.shopId = shopId;
	}
}
