package app.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Admin;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.exceptions.CouponSystemException;
import app.core.service.CompanyService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/Company")
public class CompanyController {
	@Autowired
	public CompanyService companyService;

	@PutMapping(path = "/updateCompany", headers = HttpHeaders.AUTHORIZATION)
	public void updateCompany(HttpServletRequest req,@RequestBody Company company) {
		try {
			Company unUpdatedCompany = (Company) req.getAttribute("user");
	
			if (unUpdatedCompany.equals(null)) {
				throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "user is not an Admin");
			}
			company.setId(unUpdatedCompany.getId());
			companyService.updateCompany(company);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping(path ="/addCoupon", headers = HttpHeaders.AUTHORIZATION)
	public int addCoupon(HttpServletRequest req,@RequestBody Coupon coupon)  {
		try {
			Company company = (Company) req.getAttribute("user");
			
			if (company.equals(null)) {
				throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "user is not an Admin");
			}
			coupon.setCompany(company);
			return companyService.addCoupon(coupon);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@PutMapping(path ="/updateCoupon", headers = HttpHeaders.AUTHORIZATION)
	public void updateCoupon(HttpServletRequest req,@RequestParam Coupon coupon) {
		try {
			Company company = (Company) req.getAttribute("user");
			
			if (company.equals(null)) {
				throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "user is not an Admin");
			}
			coupon.setCompany(company);
			companyService.updateCoupon(coupon);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@DeleteMapping(path = "/deleteCoupon" , headers = HttpHeaders.AUTHORIZATION)
	public void deleteCoupon(HttpServletRequest req,@RequestParam int coupon_id) {
		try {
			Company company = (Company) req.getAttribute("user");
			
			if (company.equals(null)) {
				throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "user is not an Admin");
			}
			List<Coupon> comapnyCoupons = company.getCoupons();
			for(Coupon coupon : comapnyCoupons) {
				if (coupon.getId() == coupon_id) {
					companyService.deleteCoupon(coupon_id);
				break;
				}
			}
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	
	
	@GetMapping(path ="/allMyCoupons", headers = HttpHeaders.AUTHORIZATION)
	public List<Coupon> findCouponsByCompany_Id(HttpServletRequest req) {
		try {
			Company company = (Company) req.getAttribute("user");
			
			if (company.equals(null)) {
				throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "user is not an Admin");
			}
			return (companyService.findCouponsByCompany_Id(company.getId()));
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
			
		}
	}

}
