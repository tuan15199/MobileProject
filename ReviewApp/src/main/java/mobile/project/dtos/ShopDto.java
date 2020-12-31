package mobile.project.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mobile.project.models.ShopType;

@Getter
@Setter
@NoArgsConstructor
public class ShopDto {
	private int id;
	private String name;
	private ShopType type;
	private String openTime;
	private String closeTime;
	private int minPrice;
	private int maxPrice;
	private double star;
	private String addressDetail;
	private String addressDistrict;
	private String addressCity;
	
	public ShopDto(int id, String name, ShopType type, String openTime, String closeTime, int minPrice, int maxPrice,
			double star, String addressDetail, String addressDistrict, String addressCity) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.star = star;
		this.addressDetail = addressDetail;
		this.addressDistrict = addressDistrict;
		this.addressCity = addressCity;
	}
}
