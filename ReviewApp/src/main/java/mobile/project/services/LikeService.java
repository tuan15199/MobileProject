package mobile.project.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mobile.project.dtos.LikeDto;
import mobile.project.exceptions.DataNotFoundException;
import mobile.project.exceptions.UnauthorizationException;
import mobile.project.models.Comment;
import mobile.project.models.Likes;
import mobile.project.models.User;
import mobile.project.repositories.CommentRepository;
import mobile.project.repositories.LikesRepository;
import mobile.project.repositories.UserRepository;

@Service
@Transactional
public class LikeService {
	@Autowired
	private LikesRepository likeRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CommentRepository comRepo;

	public Likes isLikeExist(int userId, int commentId) {
		Likes result = null;
		List<Likes> likes = likeRepo.getLikes(commentId);
		for (Likes like : likes) {
			if (like.getUser().getId() == userId)
				result = like;
		}
		return result;
	}

	public LikeDto addLike(LikeDto likeDto, int userId) {
		if (isLikeExist(userId, likeDto.getCommentId()) == null) {
			if (userId == likeDto.getUserId()) {
				Likes like = new Likes();
				Comment comment = new Comment();

				if (likeDto.getUserId() != null) {
					User user = userRepo.findById(likeDto.getUserId())
							.orElseThrow(() -> new DataNotFoundException("user"));
					like.setUser(user);
				}
				if (likeDto.getCommentId() != null) {
					comment = comRepo.findById(likeDto.getCommentId())
							.orElseThrow(() -> new DataNotFoundException("comment"));
					like.setComment(comment);
				}
				likeRepo.save(like);

				List<Likes> likeList = comment.getLikes();
				likeList.add(like);
				comment.setLikes(likeList);
				comment.setLikeNumber(likeList.size());
				comRepo.save(comment);

				return new LikeDto(like.getId(), likeDto.getUserId(), likeDto.getCommentId());
			} else
				throw new UnauthorizationException("you cannot like this comment");
		}
		else {
			System.out.println("hello");
			unlike(likeDto.getCommentId(), userId);
		}
		return null;
	}

	public void unlike(int comId, int userId) {
		likeRepo.deleteById(isLikeExist(userId, comId).getId());
	}

}
