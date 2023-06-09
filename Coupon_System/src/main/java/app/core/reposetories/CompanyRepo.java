package app.core.reposetories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import app.core.entities.Company;
import app.core.exceptions.CouponSystemException;


public interface CompanyRepo extends JpaRepository<Company, Integer> {

	boolean existsByEmail(String email) throws CouponSystemException;

	Company findByEmail(String email) throws CouponSystemException;
	
	

}
