package mobile.project.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSignUp {
	private String username;
	private String password;
	private String addressDetail;
	private String addressDistrict;
	private String addressCity;
}
