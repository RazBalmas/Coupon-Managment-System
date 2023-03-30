package app.core.loginManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import app.core.exceptions.CouponSystemException;
import app.core.exceptions.LoginException;
import app.core.service.AdminService;
import app.core.service.CompanyService;
import app.core.service.CustomerService;
import app.core.service.ClientService;


@Component
public class LoginManager {

	@Autowired
	private AdminService admin = new AdminService();
	@Autowired
	private ApplicationContext ctx;

	
	public ClientService login(String email, String password, Type type) throws LoginException  {

		if (type == Type.CUSTOMER) {
			CustomerService customerService = ctx.getBean(CustomerService.class);
			return customerService;
			
		}
		if (type == Type.COMPANY){
			CompanyService companyService = ctx.getBean(CompanyService.class);
			return companyService;
		}
		if(type == Type.ADMIN && admin.login(email, password)) {
			return this.admin;
		}
		else throw new LoginException("No such user found.");
	}
	

	
}
