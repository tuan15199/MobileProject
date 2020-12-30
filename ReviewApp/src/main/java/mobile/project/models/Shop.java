package mobile.project.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Shop {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String name;
	private ShopType type;
	private String openTime;
	private String closeTime;
	private int minPrice;
	private int maxPrice;
	private double star;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Address address;

	@JsonIgnore
	@OneToMany(mappedBy = "shop")
	private List<Comment> comments;
	@JsonIgnore
	@OneToMany(mappedBy = "shop")
	private List<Star> starts;
}
