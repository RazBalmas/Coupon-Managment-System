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
import org.springframework.stereotype.Service;

import app.core.entities.Admin;
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
public class AdminService extends ClientService{

	
	@Autowired
	private CompanyRepo company_repo;
	@Autowired
	private CustomerRepo customer_repo;
	@Autowired
	private CouponRepo coupon_repo;

	private Admin admin= Admin.getAdmin();
    
	/////////////////////////////////////////////////////////////////////////////////

//										Company


	/////////////////////////////////////////////////////////////////////////////////
	@Override
	public boolean login(String email, String password) {
		if (email.equals(admin.getEmail()) && password.equals(admin.getPassword())) {
			return true;
		}
		return false;
	}
	public boolean companyExistsById(int id) throws CouponSystemException {
		if (company_repo.existsById(id)) {
			return true;
		}
		return false;
	}

	public boolean companyExistsByEmail(String email) throws CouponSystemException {
		if (company_repo.existsByEmail(email)) {
			return true;
		}
		return false;
	}

	public int addCompany(Company company) throws CouponSystemException {
		if (!companyExistsByEmail(company.getEmail())) {
			Company updatedCompany = company_repo.save(company);
			return updatedCompany.getId();
		}
		return -1;
	}

	public void updateCompany(Company company) throws CouponSystemException {
		if (companyExistsByEmail(company.getEmail())) {
			company_repo.save(company);
		} else
			throw new CouponSystemException("Company was not found in DataBase to Update.");
	}

	public void deleteCompany(int id) throws CouponSystemException {
		if (companyExistsById(id)) {
			company_repo.deleteById(id);
		} else
			throw new CouponSystemException("Company was not found in DataBase.");
	}

	public List<Company> getAllCompanies() {

		return company_repo.findAll();
	}

	public Company findByCompanyId(int id) throws CouponSystemException {
		if (companyExistsById(id)) {
			try {
				Optional<Company> opt = company_repo.findById(id);
				if (opt.isPresent()) {
					Company company = opt.get();
					return company;
				}
			} catch (Exception e) {
				throw new CouponSystemException("Failed to retrive company from DataBase");
			}
		}
		throw new CouponSystemException("Company not found in DataBase by specified ID.");
	}

	public Company findByCompanyEmail(String email) throws CouponSystemException {
		if (companyExistsByEmail(email)) {
			try {
				Company company = company_repo.findByEmail(email);
				return company;
			} catch (CouponSystemException e) {

				throw new CouponSystemException("Failed to retrive Company from DataBase");
			}
		}
		throw new CouponSystemException("Company not found in DataBase by specified ID.");
	}

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

	public List<Coupon> findCouponsByCompany_Id(int company_id) throws CouponSystemException {

		List<Coupon> all_coupons = coupon_repo.findAll();
		List<Coupon> company_coupons = new ArrayList<>();
		for (Coupon coupon : all_coupons) {
			if (coupon.getCompany().getId() == company_id) {
				company_coupons.add(coupon);
			}
		}
		return company_coupons;
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
		List<Coupon> couponsByCatagory = new ArrayList<>();
		for (Coupon coupon : allCoupons) {
			if (coupon.getCatagory() == catagory) {
				couponsByCatagory.add(coupon);
			}
		}
		return couponsByCatagory;
	}
	
	public Coupon findCouponByTitle(String title) {
		
		List<Coupon> allCoupons = coupon_repo.findAll();
		for (Coupon coupon : allCoupons) {
			if (coupon.getTitle().contains(title)) {
				return coupon;
			}
		}
		System.out.println("No coupon existing by this title");
		return null;
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

	/////////////////////////////////////////////////////////////////////////////////

//										Customer

	/////////////////////////////////////////////////////////////////////////////////

	

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
		if (!existsByCustomerEmail(customer.getEmail())) {
			Customer updatedCustomer = customer_repo.save(customer);
			return updatedCustomer.getId();
		}
		else return 0;
	}

	public void updateCustomer(Customer customer) throws CouponSystemException {
		if (existsByCustomerEmail(customer.getEmail())) {
			customer_repo.save(customer);
		}

		else throw new CouponSystemException();
	}

	public void deleteCustomer(int customerID) throws CouponSystemException {
		if (CustomerExistsById(customerID)) {
			customer_repo.deleteById(customerID);
		}

		else throw new CouponSystemException();
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
	
		Coupon coupon = findByCouponId(couponID);
		Customer customer = findByCustomerId(customerID);
		
		if (couponIsValidToPurches(couponID)) {
			
			coupon.setCustomers(customer);
			Coupon customerCoupon = coupon;
			customerCoupon.setAmount(1);
			customer.addCoupon(customerCoupon);
			customer_repo.save(customer);
			coupon.setAmount(coupon.getAmount() -1);
			coupon_repo.save(coupon);
		}
	}

	public void deleteCouponPurchase(int customerID, int couponID) throws CouponSystemException{
		Coupon coupon = findByCouponId(couponID);
		Customer customer = findByCustomerId(customerID);
		
			coupon.setAmount(coupon.getAmount()+1);
			coupon.deleteCustomer(customerID);
			customer.deleteCoupon(couponID);
			customer_repo.save(customer);
			coupon_repo.save(coupon);
		}
		

}
