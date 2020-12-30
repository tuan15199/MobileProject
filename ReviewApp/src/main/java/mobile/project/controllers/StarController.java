package mobile.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import mobile.project.dtos.StarDto;
import mobile.project.models.Star;
import mobile.project.services.StarService;

@RestController
public class StarController {
	@Autowired
	private StarService service;
	
	@GetMapping(value = "stars")
	public List<Star> getAll() {
		return service.getAll();
	}
	
	@PostMapping(value = "rate/{userId}") 
	public StarDto ShopRated(@PathVariable int userId, @RequestBody StarDto starDto) {
		return service.ShopRated(starDto, userId);
	}
}
