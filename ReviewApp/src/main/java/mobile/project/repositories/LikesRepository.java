package mobile.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mobile.project.models.Likes;

public interface LikesRepository extends JpaRepository<Likes, Integer>{
	
	@Query(value = "SELECT count(likes.id) FROM likes where likes.comment_id = :id", nativeQuery = true)
	Integer getNumberOfLike(int id);
	
}
