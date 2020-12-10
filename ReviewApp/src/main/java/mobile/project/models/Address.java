package mobile.project.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String detail;
	private String district;
	private String city;

	public Address(String detail, String district, String city) {
		this.detail = detail;
		this.district = district;
		this.city = city;
	}
}