package mobile.project.services;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mobile.project.dtos.StarDto;
import mobile.project.exceptions.DataNotFoundException;
import mobile.project.exceptions.InvalidNumberException;
import mobile.project.exceptions.UnauthorizationException;
import mobile.project.models.Shop;
import mobile.project.models.Star;
import mobile.project.models.User;
import mobile.project.repositories.ShopRepository;
import mobile.project.repositories.StarRepository;
import mobile.project.repositories.UserRepository;

@Service
@Transactional
public class StarService {
	@Autowired
	private StarRepository starRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ShopRepository shopRepo;
	
	public List<Star> getAll() {
		return starRepo.findAll();
	}
	
	public Star isStarExisted(User user, Shop shop) {
		Star star = new Star();
		if(starRepo.findByUserAndShop(user, shop) != null)
			star = starRepo.findByUserAndShop(user,shop);
		else
			star = null;
		return star;
	}

	public StarDto ShopRated(StarDto starDto, int userId) {
		NumberFormat formatter = new DecimalFormat("#0.00"); 
		
		
		if (userId == starDto.getUserId()) {
			Star star = new Star();
			Shop shop = new Shop();
			User user = new User();

			if (starDto.getUserId() != null) {
				user = userRepo.findById(starDto.getUserId()).orElseThrow(() -> new DataNotFoundException("user"));
			}
			if (starDto.getShopId() != null) {
				shop = shopRepo.findById(starDto.getShopId()).orElseThrow(() -> new DataNotFoundException("comment"));
			}
			
			if(isStarExisted(user, shop) != null) {
				star = isStarExisted(user, shop);
			}
			
			star.setUser(user);
			star.setShop(shop);
			if(starDto.getStarNumber() >= 1 && starDto.getStarNumber() <= 5)
				star.setStarNumber(starDto.getStarNumber());
			else 
				throw new InvalidNumberException("Invalid star number");
			starRepo.save(star);

			List<Star> starList = shop.getStarts();
			starList.add(star);
			shop.setStarts(starList);
			
			double totalStar = 0.0;
			for(Star e: starList) {
				totalStar += e.getStarNumber();
			}
			shop.setStar(Double.parseDouble(formatter.format(totalStar/starList.size())));
			shopRepo.save(shop);

			return new StarDto(star.getId(), star.getStarNumber(), starDto.getUserId(), starDto.getShopId());
		} else
			throw new UnauthorizationException("you cannot rate this shop");
	}

}
