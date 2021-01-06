package mobile.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mobile.project.models.Comment;
import mobile.project.models.Shop;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
	@Query(value = "SELECT * FROM comment where comment.shop_id = :shopId", nativeQuery = true)
	public List<Comment> getCommentByShopId(int shopId);
}
