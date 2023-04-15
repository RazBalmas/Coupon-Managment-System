package app.core.reposetories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
@Component
public interface CustomerRepo extends JpaRepository<Customer, Integer> {

//	boolean existsByCustomerEmail(int Id) throws CouponSystemException;

//	Customer findByCustomerEmail(String email) throws CouponSystemException;

//	public List<Coupon> getCustomerCoupons(int customerID) throws CouponSystemException;
	
//	void addCouponPurchase(int customerID, int couponID) throws CouponSystemException;

//	void deleteCouponPurchase(int customerID, int couponID) throws CouponSystemException;
	
}
