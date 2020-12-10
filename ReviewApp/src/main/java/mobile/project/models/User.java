package mobile.project.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(length = 15, nullable = false)
	private String userName;
	private String password;
	@ElementCollection(fetch = FetchType.EAGER)
	List<Roles> roles;
	@OneToOne
	private Address address;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Comment> comments;
}
