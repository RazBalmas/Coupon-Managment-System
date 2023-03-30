package app.core.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import app.core.entities.Catagory;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.reposetories.CompanyRepo;
import app.core.reposetories.CouponRepo;
import app.core.reposetories.CustomerRepo;

@Service
@Transactional
public class CouponService {

	@Autowired
	private CouponRepo coupon_repo;


	/////////////////////////////////////////////////////////////////////////////////

//										Coupon

	/////////////////////////////////////////////////////////////////////////////////

	public void addCoupon(Coupon coupon) throws CouponSystemException {
		if (!coupon_repo.existsById(coupon.getId())) {
			coupon_repo.save(coupon);
		} else
			throw new CouponSystemException("Coupon cannot be added, already exists in DataBase.");
	}

	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		if (coupon_repo.existsById(coupon.getId())) {
			coupon_repo.save(coupon);
		} else
			throw new CouponSystemException("Coupon cannot be updated, cannot be found in DataBase.");

	}

	public void deleteCoupon(int coupon_id) throws CouponSystemException {
		if (coupon_repo.existsById(coupon_id)) {
			coupon_repo.deleteById(coupon_id);
		} else
			throw new CouponSystemException("Coupon cannot be found in DataBase.");

	}

	
	public Coupon findByCouponId(int coupon_id) throws CouponSystemException {
		if (coupon_repo.existsById(coupon_id)) {
			Optional<Coupon> opt = coupon_repo.findById(coupon_id);
			if (opt.isPresent()) {
				Coupon coupon = opt.get();
				return coupon;
			}
		}
		throw new CouponSystemException("Couldnt find coupon in DataBase with specified ID.");

	}

	public List<Coupon> findCouponByCatagory(Catagory catagory) {

		List<Coupon> allCoupons = coupon_repo.findAll();
		List<Coupon> couponsByCatagory = coupon_repo.findAll();
		for (Coupon coupon : allCoupons) {
			if (coupon.getCatagory() == catagory) {
				couponsByCatagory.add(coupon);
			}
		}
		return couponsByCatagory;
	}

	public boolean couponIsValidToPurches(int coupon_id) throws CouponSystemException {
		if (coupon_repo.existsById(coupon_id)) {
			Optional<Coupon> opt = coupon_repo.findById(coupon_id);
			if (opt.isPresent()) {
				Coupon coupon = opt.get();
				if (coupon.getAmount() > 0 && coupon.getEndDate().isAfter(LocalDate.now())) {
					return true;
				}
			}
		}
		return false;
	}

	public List<Coupon> getAllCoupons() {
		return coupon_repo.findAll();
	}



}
