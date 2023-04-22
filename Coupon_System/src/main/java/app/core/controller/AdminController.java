package app.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	public AdminService adminService;

	@GetMapping(path = "/companyExistsById", headers = HttpHeaders.AUTHORIZATION)
	public boolean companyExistsById(HttpServletRequest req, @RequestParam int companyId) {
		try {
			return (adminService.companyExistsById(companyId));
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping(path = "/companyExistsByEmail", headers = HttpHeaders.AUTHORIZATION)
	public boolean companyExistsByEmail(HttpServletRequest req, @RequestParam String email) {
		try {
			return (adminService.companyExistsByEmail(email));
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());

		}
	}

	@PostMapping(path = "/addCompany", headers = HttpHeaders.AUTHORIZATION, consumes = MediaType.APPLICATION_JSON_VALUE)
	public int addCompany(HttpServletRequest req, @RequestBody Company company) {
			try {
			return adminService.addCompany(company);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PutMapping(path = "/updateCompany", headers = HttpHeaders.AUTHORIZATION)
	public void updateCompany(HttpServletRequest req, @RequestBody Company company) {
				try {
			adminService.updateCompany(company);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@DeleteMapping(path = "/deleteCompany", headers = HttpHeaders.AUTHORIZATION)
	public void deleteCompany(HttpServletRequest req, @RequestParam int companyId) {
		try {
			adminService.deleteCompany(companyId);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping(path = "/getAllCompany", headers = HttpHeaders.AUTHORIZATION)
	public List<Company> getAllCompanies(HttpServletRequest req) {
		try {
		List<Company> companies = adminService.getAllCompanies();
		return companies;
		}catch (Exception e) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}

	@GetMapping(path = "/getCompanyById", headers = HttpHeaders.AUTHORIZATION)
	public Company findByCompanyId(HttpServletRequest req, @RequestParam int id) {

		try {
					return (adminService.findByCompanyId(id));
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping(path = "/getCompanyByEmail", headers = HttpHeaders.AUTHORIZATION)
	public Company findByCompanyEmailCompany(HttpServletRequest req, @RequestParam String email) {

		try {
			return (adminService.findByCompanyEmail(email));
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@PostMapping(path = "/addCoupon", headers = HttpHeaders.AUTHORIZATION)
	public void addCoupon(HttpServletRequest req, @RequestBody Coupon coupon) {

		try {
			adminService.addCoupon(coupon);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PutMapping(path = "/updateCoupon", headers = HttpHeaders.AUTHORIZATION)
	public void updateCoupon(HttpServletRequest req, @RequestBody Coupon coupon) {

		try {
				adminService.updateCoupon(coupon);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@DeleteMapping(path = "/deleteCoupon", headers = HttpHeaders.AUTHORIZATION)
	public void deleteCoupon(HttpServletRequest req, @RequestParam int coupon_id) {

		try {
			adminService.deleteCoupon(coupon_id);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping(path = "/CouponsByCompanyId", headers = HttpHeaders.AUTHORIZATION)
	public List<Coupon> findCouponsByCompany_Id(HttpServletRequest req, @RequestParam int company_id) {

		try {
			return (adminService.findCouponsByCompany_Id(company_id));
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping(path = "/CouponById", headers = HttpHeaders.AUTHORIZATION)
	public Coupon findByCouponId(HttpServletRequest req, @RequestParam int coupon_id) {
		try {
			return (adminService.findByCouponId(coupon_id));
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping(path = "/CouponByTitle", headers = HttpHeaders.AUTHORIZATION)
	public Coupon findCouponByTitle(HttpServletRequest req, @RequestParam String title) {
		Coupon coupon = adminService.findCouponByTitle(title);
		if (coupon != null) {
			return coupon;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/allCoupons", headers = HttpHeaders.AUTHORIZATION)
	public List<Coupon> getAllCoupons(HttpServletRequest req) {
		return adminService.getAllCoupons();
	}

	@GetMapping(path = "/customerExistsById", headers = HttpHeaders.AUTHORIZATION)
	public boolean CustomerExistsById(HttpServletRequest req, @RequestParam int Id) {
		try {
			return (adminService.companyExistsById(Id));
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping(path = "/customerExistsByEmail", headers = HttpHeaders.AUTHORIZATION)
	public boolean existsByCustomerEmail(HttpServletRequest req, @RequestParam String email) {
		try {
			return (adminService.existsByCustomerEmail(email));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@PostMapping(path = "/addCustomer", headers = HttpHeaders.AUTHORIZATION)
	public int addCustomer(HttpServletRequest req, @RequestBody Customer customer) {
		int customerId = adminService.addCustomer(customer);
		if (customerId == 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		return customerId;

	}

	@PutMapping(path = "/updateCustomer", headers = HttpHeaders.AUTHORIZATION)
	public void updateCustomer(HttpServletRequest req, @RequestBody Customer customer) {
		try {
			adminService.updateCustomer(customer);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@DeleteMapping(path = "/deleteCustomer", headers = HttpHeaders.AUTHORIZATION)
	public void deleteCustomer(HttpServletRequest req, @RequestParam int customerID) {
		try {
			adminService.deleteCustomer(customerID);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping(path = "/allCustomers", headers = HttpHeaders.AUTHORIZATION)
	public List<Customer> getAllCustomers() {
		return adminService.getAllCustomers();
	}

	@GetMapping(path = "/customerById", headers = HttpHeaders.AUTHORIZATION)
	public Customer findByCustomerId(HttpServletRequest req, @RequestParam int customerID) {
		try {
			return adminService.findByCustomerId(customerID);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping(path = "/customerByEmail", headers = HttpHeaders.AUTHORIZATION)
	public Customer findByCustomerEmail(HttpServletRequest req, @RequestParam String email) {
		try {
			return adminService.findByCustomerEmail(email);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping(path = "/customerCoupons", headers = HttpHeaders.AUTHORIZATION)
	public List<Coupon> getCustomerCoupons(HttpServletRequest req, @RequestParam int customerID) {
		try {
			return adminService.getCustomerCoupons(customerID);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@PostMapping(path = "/addPurches", headers = HttpHeaders.AUTHORIZATION)
	public void addCouponPurchase(HttpServletRequest req, @RequestParam int customerID, @RequestParam int couponID) {
		try {
			adminService.addCouponPurchase(customerID, couponID);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@DeleteMapping(path = "/deletePurches", headers = HttpHeaders.AUTHORIZATION)
	public void deleteCouponPurchase(HttpServletRequest req, int customerID, int couponID) {
		try {
			adminService.deleteCouponPurchase(customerID, couponID);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

}
