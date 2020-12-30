package mobile.project.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Star {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private int starNumber;
	@ManyToOne
	private User user;
	@ManyToOne
	private Shop shop;
}
