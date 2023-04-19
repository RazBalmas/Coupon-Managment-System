package app.core.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
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
@Scope("prototype")
@Transactional
//@Qualifier("CustomerService")
public class CustomerService extends ClientService{

	
	@Autowired
	private CustomerRepo customer_repo;
	@Autowired
	private CouponRepo coupon_repo;

	/////////////////////////////////////////////////////////////////////////////////

//										Customer

	/////////////////////////////////////////////////////////////////////////////////

	

	@Override
	public boolean login(String email, String password) {
		try {
			if (findByCustomerEmail(email).getPassword().equals(password)) {
				return true;
			}
		} catch (CouponSystemException e) {
			System.out.println("Wrong email or Password");
		}
		return false;
	}

	public boolean CustomerExistsById(int Id) {
		return customer_repo.existsById(Id);
	}

	public boolean existsByCustomerEmail(String email) {
		List<Customer> allCustomers = customer_repo.findAll();
		for (Customer customer : allCustomers) {
			if (customer.getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}

	public int addCustomer(Customer customer) {
		if (!customer_repo.existsById(customer.getId())) {
			Customer cust = customer_repo.save(customer);
			return cust.getId();
		}
		return 0;
	}

	public void updateCustomer(Customer customer) {
		if (existsByCustomerEmail(customer.getEmail())) {
			 customer_repo.save(customer);
		}

	}

	public void deleteCustomer(int customerID) {
		if (CustomerExistsById(customerID)) {
			customer_repo.deleteById(customerID);
		}

	}

	public List<Customer> getAllCustomers() {
		return customer_repo.findAll();
	}

	public Customer findByCustomerId(int customerID) throws CouponSystemException {
		Optional<Customer> opt = customer_repo.findById(customerID);
		if (opt.isPresent()) {
			Customer customer = opt.get();
			return customer;
		}
		throw new CouponSystemException("Could not find Customer by specified ID.");
	}

	public Customer findByCustomerEmail(String email) throws CouponSystemException {
		List<Customer> allCustomer = customer_repo.findAll();
		for (Customer customer : allCustomer) {
			if (customer.getEmail().equals(email)) {
				return customer;
			}
		}
		throw new CouponSystemException("Could not find Customer by specified ID.");

	}

	public List<Coupon> getCustomerCoupons(int customerID) throws CouponSystemException {

		Customer customer = findByCustomerId(customerID);
		return customer.getOwned_coupons();
	}

	public void addCouponPurchase(int customerID, int couponID) throws CouponSystemException{
	
		
		Customer customer = findByCustomerId(customerID);
		
		Optional<Coupon> opt = coupon_repo.findById(couponID);
		if (opt.isPresent()) {
			Coupon coupon = opt.get();
		if (couponIsValidToPurches(couponID)) {
			
			coupon.setCustomers(customer);
			customer.addCoupon(coupon);
			customer_repo.save(customer);
			coupon_repo.save(coupon);
		}
		}
	}

	public void deleteCouponPurchase(int customerID, int couponID) throws CouponSystemException{
		Optional<Coupon> opt = coupon_repo.findById(couponID);
		if (opt.isPresent()) {
			Coupon coupon = opt.get();
			Customer customer = findByCustomerId(customerID);
			coupon.setAmount(coupon.getAmount()+1);
			coupon.deleteCustomer(customerID);
			customer.deleteCoupon(couponID);
			customer_repo.save(customer);
			coupon_repo.save(coupon);
		}
		else {
			System.out.println("Coupon not in DataBase by specified ID");
		}
		
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
		

}
