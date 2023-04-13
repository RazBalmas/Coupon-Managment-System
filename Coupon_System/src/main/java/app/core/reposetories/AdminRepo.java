package app.core.reposetories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Admin;

public interface AdminRepo extends JpaRepository<Admin, Long> {
    Optional<Admin> findFirstByOrderByIdAsc();

	boolean existsById(int i);



}
