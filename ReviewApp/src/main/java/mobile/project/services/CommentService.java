package mobile.project.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mobile.project.dtos.CommentDto;
import mobile.project.exceptions.DataNotFoundException;
import mobile.project.models.Comment;
import mobile.project.models.Shop;
import mobile.project.models.User;
import mobile.project.repositories.CommentRepository;
import mobile.project.repositories.LikesRepository;
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

	@Autowired
	private LikesRepository likeRepo;

	public List<CommentDto> getAll() {
		List<Comment> comments = commentRepo.findAll();
		List<CommentDto> result = new ArrayList<>();

		for (Comment comment : comments) {
			comment.setLikeNumber(likeRepo.getNumberOfLike(comment.getId()));

			result.add(
					new CommentDto(comment.getId(), comment.getContent(), comment.getPostDate(), comment.getPostTime(),
							comment.getLikeNumber(), comment.getUser().getId(), comment.getShop().getId()));
		}

		return result;
	}

	public CommentDto addComment(CommentDto comDto) {
		Comment comment = new Comment();

		comment.setContent(comDto.getContent());
		comment.setPostDate(LocalDate.now());
		comment.setPostTime(LocalTime.now());

		if (comDto.getUserId() != null) {
			User user = userRepo.findById(comDto.getUserId()).orElseThrow(() -> new DataNotFoundException("user"));
			comment.setUser(user);
		}
		if (comDto.getShopId() != null) {
			Shop shop = shopRepo.findById(comDto.getShopId()).orElseThrow(() -> new DataNotFoundException("user"));
			comment.setShop(shop);
		}
		commentRepo.save(comment);

		return new CommentDto(comment.getId(), comment.getContent(), comment.getPostDate(), comment.getPostTime(), 0,
				comDto.getUserId(), comDto.getShopId());
	}

}
