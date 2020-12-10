package mobile.project.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mobile.project.exceptions.DataNotFoundException;
import mobile.project.models.Shop;
import mobile.project.repositories.ShopRepository;

@Service
@Transactional
public class ShopService {
	@Autowired ShopRepository repo;
	
	// get all shops
	public List<Shop> getAll() {
		return repo.findAll();
	}
	
	// get shop by id
	public Shop getById(int id) {
		return repo.findById(id).orElseThrow(() -> new DataNotFoundException("shop"));
	}
	
}
