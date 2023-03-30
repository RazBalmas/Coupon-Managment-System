package app.core.reposetories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Catagory;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;

public interface CompanyRepo extends JpaRepository<Company, Integer> {

	boolean existsByEmail(String email) throws CouponSystemException;

	Company findByEmail(String email) throws CouponSystemException;
	
	

}
