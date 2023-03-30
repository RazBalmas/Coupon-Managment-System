package app.core.reposetories;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Catagory;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;

public interface CouponRepo extends JpaRepository<Coupon, Integer> {

	List<Coupon> findByCompany_Id(int company_id) throws CouponSystemException;

	Coupon findByCatagory(Catagory catagory);

	
}
