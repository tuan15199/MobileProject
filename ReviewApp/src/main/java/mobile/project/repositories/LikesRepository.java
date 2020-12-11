package mobile.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mobile.project.models.Likes;

public interface LikesRepository extends JpaRepository<Likes, Integer>{
	
	@Query(value = "SELECT count(likes.id) FROM likes where likes.comment_id = :id", nativeQuery = true)
	Integer getNumberOfLike(int id);
	
	@Query(value = "SELECT * FROM likes where likes.comment_id = :id", nativeQuery = true)
	List<Likes> getLikes(int id);
}
