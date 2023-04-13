package app.core.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.core.entities.Admin;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.entities.User;
import app.core.loginManager.ClientType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

@Component
public class JwtUtil extends JwtUtilAbstract<User, Integer> {

	@Override
	public String generateToken(User user) {
		// create a map of all needed claims
		Map<String, Object> claims = new HashMap<>();
		claims.put("email", user.getEmail());
		claims.put("clientType", user.getClientType());
		claims.put("password", user.getPassword());
		String userClientType = user.getClientType().name();

		switch (userClientType) {

		case "COMPANY":
			claims.put("name", user.getName());
			
			List<Coupon> couponList = user.getCouponList();
			if (couponList != null) {
				Map<Integer, Coupon> couponMap = new HashMap<>();
				for (Coupon coupon : couponList) { // converting List to a map
					couponMap.put(coupon.getId(), coupon);
				}
				
				Map<String, Object> couponClaims = new HashMap<>();
				for (Map.Entry<Integer, Coupon> entry : couponMap.entrySet()) {
					couponClaims.put(String.valueOf(entry.getKey()), entry.getValue());
				}
				claims.putAll(couponClaims);
			}
			String companytoken = this.createToken(claims, user.getId());
			return companytoken;
			

					
		case "CUSTOMER":
			claims.put("first name", user.getFirstName());
			claims.put("last name", user.getLastName());
			List<Coupon> ownedCouponsList = user.getCouponList();
			Map<Integer, Coupon> ownedCouponMap = new HashMap<>();
			for (Coupon coupon : ownedCouponsList) { // converting List to a map
				ownedCouponMap.put(coupon.getId(), coupon);
			}

			Map<String, Object> ownedCouponClaims = new HashMap<>();
			for (Map.Entry<Integer, Coupon> entry : ownedCouponMap.entrySet()) {
				ownedCouponClaims.put(String.valueOf(entry.getKey()), entry.getValue());
			}
			claims.putAll(ownedCouponClaims);
			String customerToken = this.createToken(claims, user.getId());
			return customerToken;

		case "ADMIN":
			String adminToken = this.createToken(claims, user.getId());
			return adminToken;
		}
		throw new JwtException("Bad request, type undefined");

		
	}

	@Override
	public User extractUser(String token) throws JwtException {

		Claims claims = this.extractAllClaims(token);
		ClientType clientType = ClientType.valueOf(claims.get("clientType", String.class));
		String userClientType = clientType.name();
		switch (userClientType) {

		case "COMPANY":
			return extractCompany(token);

		case "CUSTOMER":

			return extractCustomer(token);

		case "ADMIN":
			Admin admin = Admin.getAdmin();
			return admin;

		}
		return null;

	}
	
	
	
	private Company extractCompany(String token) {
		Claims claims = this.extractAllClaims(token);
		int id = Integer.parseInt(claims.getSubject());
		String email = claims.get("email", String.class);
		String name = claims.get("name", String.class);
		String password = claims.get("password", String.class);
		Company company = new Company(id, name, email, password);

		if (claims.get("CouponClaims") instanceof Map) { // if company has coupons
			Map<String, Object> couponMapClaims = (Map<String, Object>) claims.get("CouponClaims");
			List<Coupon> couponList = new ArrayList<>();
			for (Map.Entry<String, Object> entry : couponMapClaims.entrySet()) {
				Integer couponId = Integer.parseInt(entry.getKey());
				ObjectMapper mapper = new ObjectMapper();
				Coupon coupon = mapper.convertValue(entry.getValue(), Coupon.class);
				couponList.add(coupon);
			}
			company.setCoupons(couponList);
		}

		return company;

	}

	private Customer extractCustomer(String token) {
		Claims claims = this.extractAllClaims(token);
		int id = Integer.parseInt(claims.getSubject());
		String email = claims.get("email", String.class);
		String customerFirstName = claims.get("firstName", String.class);
		String customerLastName = claims.get("lastName", String.class);
		String customerPassword = claims.get("password", String.class);
		Customer customer = new Customer(id, customerFirstName, customerLastName, email, customerPassword, null);

		if (claims.get("ownedCouponClaims") instanceof Map) { // if company has coupons
			Map<String, Object> couponMapClaims = (Map<String, Object>) claims.get("ownedCouponClaims");
			List<Coupon> couponList = new ArrayList<>();
			for (Map.Entry<String, Object> entry : couponMapClaims.entrySet()) {
				Integer couponId = Integer.parseInt(entry.getKey());
				ObjectMapper mapper = new ObjectMapper();
				Coupon coupon = mapper.convertValue(entry.getValue(), Coupon.class);
				coupon.setId(couponId);
				couponList.add(coupon);
			}
			customer.setOwned_coupons(couponList);
		}
		return customer;

	}

}
