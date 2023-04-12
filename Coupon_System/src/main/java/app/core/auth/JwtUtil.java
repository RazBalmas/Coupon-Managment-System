package app.core.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			
			case "COMPANY" :
				Company company = (Company)user;
				claims.put("name", company.getName());
				
				List<Coupon> couponList = company.getCoupons();
				Map<Integer, Coupon> couponMap = new HashMap<>();
				for (Coupon coupon : couponList) { // converting List to a map 
					couponMap.put(coupon.getId(), coupon);
				}
								
				Map<String, Object> couponClaims = new HashMap<>();
				for (Map.Entry<Integer, Coupon> entry : couponMap.entrySet()) {
				    couponClaims.put(String.valueOf(entry.getKey()), entry.getValue());
				}
				claims.putAll(couponClaims);
				break;
		
			case "CUSTOMER" :
				Customer customer = (Customer)user;
				claims.put("first name", customer.getFirstName());
				claims.put("last name", customer.getLastName());
				List<Coupon> ownedCouponsList = customer.getOwned_coupons();
				Map<Integer, Coupon> ownedCouponMap = new HashMap<>();
				for (Coupon coupon : ownedCouponsList) { // converting List to a map 
					ownedCouponMap.put(coupon.getId(), coupon);
				}
				
				Map<String, Object> ownedCouponClaims = new HashMap<>();
				for (Map.Entry<Integer, Coupon> entry : ownedCouponMap.entrySet()) {
					ownedCouponClaims.put(String.valueOf(entry.getKey()), entry.getValue());
				}
				claims.putAll(ownedCouponClaims);
				break;
				
			case "ADMIN" :
				Admin admin = (Admin)user;				
				break;				
		}
		
		String token = this.createToken(claims, user.getId());
		return token;
	}

	@Override
	public User extractUser(String token) throws JwtException {
		User user = new User();
		
		Claims claims = this.extractAllClaims(token);
		int id = Integer.parseInt(claims.getSubject());
		String email = claims.get("email", String.class);
		ClientType clientType = ClientType.valueOf(claims.get("clientType", String.class));
		String userClientType = clientType.name();
		switch (userClientType) {
		
		case "COMPANY" :
			
			String name = claims.get("name", String.class);
			String password = claims.get("password", String.class);	
			Company company = new Company(id, name ,email, password);
		
			if(claims.get("ownedCouponClaims") instanceof Map) { //if company has coupons
			Map<String, Object> couponMapClaims = (Map<String, Object>) claims.get("ownedCouponClaims");
			List<Coupon> couponList = new ArrayList<>();
			for (Map.Entry<String, Object> entry : couponMapClaims.entrySet()) {
			    Integer couponId = Integer.parseInt(entry.getKey());
			    ObjectMapper mapper = new ObjectMapper();
			    Coupon coupon = mapper.convertValue(entry.getValue(), Coupon.class);
			    couponList.add(coupon);
			}
			company.setCoupons(couponList);
			} 		
			user = company;
			break;
			
		case "CUSTOMER" :
			
			String customerFirstName = claims.get("firstName", String.class);
			String customerLastName = claims.get("lastName", String.class);
			String customerPassword = claims.get("password", String.class);	
			Customer customer= new Customer(id, customerFirstName ,customerLastName ,email, customerPassword, null);

			if(claims.get("couponClaims") instanceof Map) { //if company has coupons
				Map<String, Object> couponMapClaims = (Map<String, Object>) claims.get("couponClaims");
				List<Coupon> couponList = new ArrayList<>();
				for (Map.Entry<String, Object> entry : couponMapClaims.entrySet()) {
					Integer couponId = Integer.parseInt(entry.getKey());
					ObjectMapper mapper = new ObjectMapper();
					Coupon coupon = mapper.convertValue(entry.getValue(), Coupon.class);
					coupon.setId(id);
					couponList.add(coupon);
				}
				customer.setOwned_coupons(couponList);
			} 	
			user = customer;
			break;
			
		case "ADMIN" :
					Admin admin = Admin.getAdmin();
					user = admin;
			break;				
			}
		
		return user;
	} 

	
	
}
