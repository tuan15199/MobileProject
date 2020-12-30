package mobile.project.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginReturnDto {
	private int errorCode;
	private Token data;
	private String errorMessage = null;
}
