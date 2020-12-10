package mobile.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import mobile.project.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

}
