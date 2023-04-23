package app.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Catagory;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.service.CouponService;
import app.core.service.CustomerService;
import app.core.service.FileStorageService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/Customer")
public class CustomerController{
	@Autowired
	public CustomerService customerService;
	@Autowired
	public CouponService couponService;
	@Autowired
	public FileStorageService fileStorageService;
	
	
	
	@PutMapping(path ="/updateCustomer", headers = HttpHeaders.AUTHORIZATION)
	public void updateCustomer(HttpServletRequest req,@RequestBody Customer customer) {
		try {
			Customer unUpdatedCustomer = (Customer) req.getAttribute("CUSTOMER");
			if (unUpdatedCustomer.equals(null)) {
				throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "user is not an Company");
			}
			customer.setId(unUpdatedCustomer.getId());
			customerService.updateCustomer(customer);
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	

	@GetMapping(path ="/customerCoupons", headers = HttpHeaders.AUTHORIZATION)
	public List<Coupon> getCustomerCoupons(HttpServletRequest req){

			try {
				Customer customer = (Customer) req.getAttribute("CUSTOMER");
				if (customer.equals(null)) {
					throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "user is not an Company");
				}
				return customerService.getCustomerCoupons(customer.getId());
			} catch (CouponSystemException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());	
			}
			}
	
	@PostMapping(path = "/addPurches", headers = HttpHeaders.AUTHORIZATION)
	public void addCouponPurchase(HttpServletRequest req,@RequestParam int customerID,@RequestParam int couponID) {
		try {
		 customerService.addCouponPurchase(customerID, couponID);
		}catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());	
		}
	}
	
	@DeleteMapping(path ="/deletePurches", headers = HttpHeaders.AUTHORIZATION)
	public void deleteCouponPurchase(HttpServletRequest req,@RequestParam int customerID,@RequestParam int couponID) {
		try {
			customerService.deleteCouponPurchase(customerID, couponID);
		}catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());	
		}
	}
	
	@GetMapping(path ="/findCouponById", headers = HttpHeaders.AUTHORIZATION)
	public Coupon findByCouponId(HttpServletRequest req,@RequestParam int coupon_id){
		
		try {
			Coupon coupon = couponService.findByCouponId(coupon_id);
			System.out.println(coupon);
			return coupon;
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());	
		}
	}
	
	@GetMapping(path ="/findCouponByCatagory", headers = HttpHeaders.AUTHORIZATION)
	public List<Coupon> findCouponByCatagory(HttpServletRequest req,@RequestParam Catagory catagory){
			return couponService.findCouponByCatagory(catagory);
		} 

	@GetMapping(path ="/allCoupons", headers = HttpHeaders.AUTHORIZATION)
	public List<Coupon> getAllCoupons(HttpServletRequest req){
		return couponService.getAllCoupons();
	} 

	@PostMapping(path = "/uploadImage")
	public String uploadFile(@RequestParam MultipartFile file) {
		return this.fileStorageService.storeFile(file);
	}

	

}
