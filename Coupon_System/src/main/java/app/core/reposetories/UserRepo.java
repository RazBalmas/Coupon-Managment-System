package app.core.reposetories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import app.core.entities.User;
@Component
public interface UserRepo extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);
}
