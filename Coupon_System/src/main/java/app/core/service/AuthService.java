package app.core.service;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.core.auth.JwtUtil;
import app.core.auth.UserCredentials;
import app.core.entities.Admin;
import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.entities.User;
import app.core.exceptions.CouponSystemException;
import app.core.exceptions.LoginException;
import app.core.loginManager.ClientType;
import app.core.reposetories.*;

@Service
@Transactional
public class AuthService {

	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private CompanyRepo companyRepo;
	@Autowired
	private CustomerRepo customerRepo;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private CustomerService customerService;


	public String register(User user) throws LoginException {
		if (user.getClientType() == ClientType.COMPANY) {
			Company company = userToCompany(user);
			companyRepo.save(company);
			company = companyRepo.save(company);
			String token = this.jwtUtil.generateCompanyToken(company);
			return token;
					}
		if (user.getClientType() == ClientType.CUSTOMER) {
			Customer customer  = userToCustomer(user);
			customerRepo.save(customer);
			customerRepo.save(customer);
			customer = customerRepo.save(customer);
			String token = this.jwtUtil.generateCustomerToken(customer);
			return token;
		}
		throw new LoginException("User already exists in Database.");

	}

	public String login(UserCredentials userCredentials) throws CouponSystemException {
		if (userCredentials.getClientType() == ClientType.ADMIN ) {
					if(adminService.login(userCredentials.getEmail(), userCredentials.getPassword())) {
						Admin admin = Admin.getAdmin();
						return this.jwtUtil.generateAdminToken(admin);
					}
				}
		
		if (userCredentials.getClientType() ==ClientType.COMPANY ) {
				if(companyService.login(userCredentials.getEmail(), userCredentials.getPassword())) {
					Company company = companyService.findByCompanyEmail(userCredentials.getEmail());
					return this.jwtUtil.generateCompanyToken(company);
				}
			
		}
		
		
		if (userCredentials.getClientType() ==ClientType.CUSTOMER ) {
				if(customerService.login(userCredentials.getEmail(), userCredentials.getPassword())) {
					Customer customer = customerService.findByCustomerEmail(userCredentials.getEmail());
					return this.jwtUtil.generateCustomerToken(customer);
				}
			}
		
		
			throw new LoginException("loging failed - wrong role");
		}


	
	private Company userToCompany(User user) {
		
		Company company = new Company();
		company.setEmail(user.getEmail());
		company.setPassword(user.getPassword());
			return company;
	
	}

	private Customer userToCustomer(User user) {
		
		Customer customer = new Customer();
		customer.setEmail(user.getEmail());
		customer.setPassword(user.getPassword());
		return customer;
			
	}
	
	
}
