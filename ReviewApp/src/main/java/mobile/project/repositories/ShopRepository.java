package mobile.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mobile.project.dtos.ShopDto;
import mobile.project.models.Shop;

public interface ShopRepository extends JpaRepository<Shop, Integer>{
	@Query(value = "SELECT * FROM shop where shop.type = :type", nativeQuery = true)
	public List<Shop> getShopByType(int type);
	
	@Query(value = "SELECT a.city FROM shop s JOIN address a WHERE s.address_id = a.id GROUP BY a.city", nativeQuery = true)
	public List<String> getAllCities();
	
	@Query(value = "SELECT * FROM shop s JOIN address a WHERE s.address_id = a.id HAVING a.city = :city", nativeQuery = true)
	public List<Shop> getShopByCity(String city);
	
}
