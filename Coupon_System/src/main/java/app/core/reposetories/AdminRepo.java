package app.core.reposetories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import app.core.entities.Admin;
@Component
public interface AdminRepo extends JpaRepository<Admin, Long> {
    Optional<Admin> findFirstByOrderByIdAsc();

	boolean existsById(int i);



}
