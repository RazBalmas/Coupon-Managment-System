package app.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Catagory;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.service.CouponService;
import app.core.service.CustomerService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/Customer")
public class CustomerController{
	@Autowired
	public CustomerService customerService;
	@Autowired
	public CouponService couponService;
		
	@PostMapping("/addCustomer")
		public int addCustomer(@RequestBody Customer customer) {
				int customerId = customerService.addCustomer(customer);
				if (customerId == 0) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST);		
				}
				return customerId;
					}
	
	
	@PutMapping("/updateCustomer")
	public void updateCustomer(@RequestBody Customer customer) {
			customerService.updateCustomer(customer);
		
	}
	

	@GetMapping("/customerCoupons")
	public List<Coupon> getCustomerCoupons(int customerID){

			try {
				return customerService.getCustomerCoupons(customerID);
			} catch (CouponSystemException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());	
			}
			}
	
	@PostMapping("/addPurches")
	public void addCouponPurchase(@RequestParam int customerID,@RequestParam int couponID) {
		try {
		 customerService.addCouponPurchase(customerID, couponID);
		}catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());	
		}
	}
	
	@DeleteMapping("/deletePurches")
	public void deleteCouponPurchase(@RequestParam int customerID,@RequestParam int couponID) {
		try {
			customerService.deleteCouponPurchase(customerID, couponID);
		}catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());	
		}
	}
	
	@GetMapping("/findCouponById")
	public Coupon findByCouponId(@RequestParam int coupon_id){
		
		try {
			return couponService.findByCouponId(coupon_id);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());	
		}
	}
	
	@GetMapping("/findCouponByCatagory")
	public List<Coupon> findCouponByCatagory(@PathVariable Catagory catagory){
			return couponService.findCouponByCatagory(catagory);
		} 

	@GetMapping("/allCoupons")
	public List<Coupon> getAllCoupons(){
		return couponService.getAllCoupons();
	} 
	
	

}
