package mobile.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import mobile.project.models.Likes;

public interface LikesRepository extends JpaRepository<Likes, Integer>{

}
