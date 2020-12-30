package mobile.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mobile.project.models.Shop;

public interface ShopRepository extends JpaRepository<Shop, Integer>{
	@Query(value = "SELECT * FROM shop where shop.type = :type", nativeQuery = true)
	public Shop getShopByType(int type);
}
