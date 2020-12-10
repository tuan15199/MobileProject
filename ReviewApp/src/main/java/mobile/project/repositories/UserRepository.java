package mobile.project.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import mobile.project.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	boolean existsByUserName(String username);

	User findByUserName(String username);

	@Transactional
	void deleteByUserName(String username);
}
