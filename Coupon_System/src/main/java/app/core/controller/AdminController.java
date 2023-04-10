package app.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.service.AdminService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/admin")
public class AdminController{

	@Autowired
	public AdminService adminService;

	@GetMapping("/companyExistsById")
	public boolean companyExistsById(int id) {
	
		try {
			return (adminService.companyExistsById(id));
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@GetMapping("/companyExistsByEmail")
	public boolean companyExistsByEmail(@RequestParam String email) {
		try {
		return (adminService.companyExistsByEmail(email));
	} catch (CouponSystemException e) {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		
			}}
	
	
	@PostMapping("/addCompany")
	public int addCompany(@RequestBody Company company) {
		try {
			return (adminService.addCompany(company));
		}
		catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
				}}
	
	@PutMapping("/updateCompany")
	public void updateCompany(@RequestBody Company company) {
		try {
			adminService.updateCompany(company);
		}
		catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());		
		}}
	
	@DeleteMapping("/deleteCompany")
	public void deleteCompany(@RequestParam int companyId) {
		try {
		 adminService.deleteCompany(companyId);
		}
		catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());		
		}}

	@GetMapping("/getAllCompany")
	public List<Company> getAllCompanies() {
		List<Company> companies = adminService.getAllCompanies();
		System.out.println(companies.toString());
		return companies;
	}
	
	@GetMapping("/getCompanyById")
	public Company findByCompanyId(@RequestParam int id) {
		
		try {
			return (adminService.findByCompanyId(id));
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping("/getCompanyByEmail")
	public Company findByCompanyEmailCompany (@RequestParam String email) {
		
		try {
			return (adminService.findByCompanyEmail(email));
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@PostMapping("/addCoupon")
	public void addCoupon(@RequestBody Coupon coupon) {
		
		try {
		adminService.addCoupon(coupon);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PutMapping("/updateCoupon")
	public void updateCoupon(@RequestBody Coupon coupon) {
		
		try {
			adminService.updateCoupon(coupon);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@DeleteMapping("/deleteCoupon")
	public void deleteCoupon(@RequestParam int coupon_id) {
		
		try {
			adminService.deleteCoupon(coupon_id);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@GetMapping("/CouponsByCompanyId")
	public  List<Coupon> findCouponsByCompany_Id(@RequestParam int company_id) {
		
		try {
			return (adminService.findCouponsByCompany_Id(company_id));
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@GetMapping("/CouponById")
	public Coupon findByCouponId(int coupon_id) {
		try {
			return (adminService.findByCouponId(coupon_id));
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping("/CouponByTitle")
	public Coupon findCouponByTitle(String title)  {
		 Coupon coupon = adminService.findCouponByTitle(title);
		 if (coupon != null) {
			 return coupon;
		 }
		 else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/allCoupons")
	public  List<Coupon> getAllCoupons() {
		return adminService.getAllCoupons();
		}
	
	
	@GetMapping("/customerExistsById")
	public boolean CustomerExistsById(@RequestParam int Id) {
		try {
			return (adminService.companyExistsById(Id));
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@GetMapping("/customerExistsByEmail")
	public boolean existsByCustomerEmail(@RequestParam String email) {
		try {
			return (adminService.companyExistsByEmail(email));
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
		}
		
	@PostMapping("/addCustomer")
		public int addCustomer(@RequestBody Customer customer) {
				int customerId = adminService.addCustomer(customer);
				if (customerId == 0) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST);		
				}
				return customerId;
			
		
	}
	
	@PutMapping("/updateCustomer")
	public void updateCustomer(@RequestBody Customer customer) {
		try {
			adminService.updateCustomer(customer);
		}catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
				}	
	}
	
	
	@DeleteMapping("/deleteCustomer")
	public void deleteCustomer(@RequestParam int customerID) {
		try {
			adminService.deleteCustomer(customerID);
		}catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());	
		}
	}

	@GetMapping("/allCustomers")
	public List<Customer> getAllCustomers() {
			return adminService.getAllCustomers();
	}
	
	@GetMapping("/customerById")
	public Customer findByCustomerId(@RequestParam int customerID){
		try {
			return adminService.findByCustomerId(customerID);
		}catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());	
		}
	}
	
	@GetMapping("/customerByEmail")
	public  Customer findByCustomerEmail(@RequestParam String email){
		try {
			return adminService.findByCustomerEmail(email);
		}catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());	
		}
	}

	@GetMapping("/customerCoupons")
	public List<Coupon> getCustomerCoupons(int customerID){
		try {
			return adminService.getCustomerCoupons(customerID);
		}catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());	
		}
	}
	
	@PostMapping("/addPurches")
	public void addCouponPurchase(@RequestParam int customerID,@RequestParam int couponID) {
		try {
		 adminService.addCouponPurchase(customerID, couponID);
		}catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());	
		}
	}
	
	@DeleteMapping("/deletePurches")
	public void deleteCouponPurchase(int customerID, int couponID) {
		try {
			adminService.deleteCouponPurchase(customerID, couponID);
		}catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());	
		}
	}
	
	
}
	

