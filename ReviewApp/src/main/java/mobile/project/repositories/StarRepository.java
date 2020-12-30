package mobile.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import mobile.project.models.Shop;
import mobile.project.models.Star;
import mobile.project.models.User;

public interface StarRepository extends JpaRepository<Star, Integer>{
	public Star findByUserAndShop(User user, Shop shop);
}
