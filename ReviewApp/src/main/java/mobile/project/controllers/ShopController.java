package mobile.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import mobile.project.models.Shop;
import mobile.project.services.ShopService;

@RestController
public class ShopController {
	@Autowired ShopService service;
	
	// get all shops
	@GetMapping(value = "/shops")
	public List<Shop> getAll() {
		return service.getAll();
	}
	
	// get shop by id
	@GetMapping(value = "/shop/{id}")
	public Shop getById(@PathVariable int id) {
		return service.getById(id);
	}
	
	// get shop by type
	@GetMapping(value = "shop/type/{type}")
	public Shop getShopByType(@PathVariable int type) {
		return service.getShopByType(type);
	}
	
}
