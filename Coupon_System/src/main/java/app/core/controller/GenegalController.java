package app.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Coupon;
import app.core.exceptions.CouponSystemException;
import app.core.service.AdminService;
import app.core.service.CouponService;

@RestController
@RequestMapping("/api/general")
public class GenegalController {
	
	@Autowired
	public CouponService couponService;
	
	@GetMapping(path = "/allCoupons")
	public List<Coupon> getAllCoupons(HttpServletRequest req) {
		return couponService.getAllCoupons();
	}

}
