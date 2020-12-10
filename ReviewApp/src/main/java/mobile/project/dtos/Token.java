package mobile.project.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Token {
	private int userId;
	private String userName;
	private String accessToken;

}
