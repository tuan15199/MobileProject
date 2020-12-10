package mobile.project.services;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mobile.project.dtos.CommentDto;
import mobile.project.exceptions.DataNotFoundException;
import mobile.project.models.Comment;
import mobile.project.models.Shop;
import mobile.project.models.User;
import mobile.project.repositories.CommentRepository;
import mobile.project.repositories.ShopRepository;
import mobile.project.repositories.UserRepository;

@Service
@Transactional
public class CommentService {
	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired 
	private UserRepository userRepo;
	
	@Autowired
	private ShopRepository shopRepo;
	
	
	public CommentDto addComment(CommentDto comDto) {
		Comment comment = new Comment();
		User user = new User();
		Shop shop = new Shop();
		
		comment.setContent(comDto.getContent());
		comment.setPostDate(LocalDate.now());
		comment.setPostTime(LocalTime.now());
		
		if(comDto.getUserId() != null) {
			user = userRepo.findById(comDto.getUserId()).orElseThrow(() -> new DataNotFoundException("user"));
			comment.setUser(user);
		}
		if(comDto.getShopId() != null) {
			shop = shopRepo.findById(comDto.getShopId()).orElseThrow(() -> new DataNotFoundException("user"));
			comment.setShop(shop);
		}
		
		commentRepo.save(comment);
		
		return new CommentDto(comment.getId(), comment.getContent(), comment.getPostDate(), comment.getPostTime(), comDto.getUserId(), comDto.getShopId());
	}
}
