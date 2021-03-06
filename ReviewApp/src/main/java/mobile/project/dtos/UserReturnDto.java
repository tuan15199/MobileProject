package mobile.project.dtos;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mobile.project.models.Roles;

@Getter
@Setter
@NoArgsConstructor
public class UserReturnDto {
	private Integer id;
	private String username;
	private String addressDetail;
	private String addressDistrict;
	private String addressCity;
	List<Roles> roles;
	
	public UserReturnDto(Integer id, String username, String addressDetail, String addressDistrict,
			String addressCity, List<Roles> roles) {
		this.id = id;
		this.username = username;
		this.addressDetail = addressDetail;
		this.addressDistrict = addressDistrict;
		this.addressCity = addressCity;
		this.roles = roles;
	}
	
}
