package mobile.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import mobile.project.dtos.ShopDto;
import mobile.project.models.Shop;
import mobile.project.services.ShopService;

@RestController
public class ShopController {
	@Autowired ShopService service;
	
	// get all shops
	@GetMapping(value = "/shops")
	public List<ShopDto> getAll() {
		return service.getAll();
	}
	
	// get shop by id
	@GetMapping(value = "/shops/{id}")
	public ShopDto getById(@PathVariable int id) {
		return service.getById(id);
	}
	
	// get shop by type
	@GetMapping(value = "shops/type/{type}")
	public Shop getShopByType(@PathVariable int type) {
		return service.getShopByType(type);
	}
	
	// create shop
	@PostMapping(value = "shops")
	public Shop createShop(@RequestBody ShopDto shopDto) {
		return service.createShop(shopDto);
	}
	
	// update shop
	@PutMapping(value = "shops/{id}")
	public Shop updateShop(@PathVariable int id, @RequestBody ShopDto shopDto) {
		return service.updateShop(id, shopDto);
	}
	
	// delete shop
	@DeleteMapping(value = "shops/{id}")
	public void deleteShop(@PathVariable int id) {
		service.delete(id);
	}
	
}
