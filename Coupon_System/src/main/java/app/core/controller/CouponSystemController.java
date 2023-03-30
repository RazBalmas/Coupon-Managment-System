//package app.core.controller;
//
//import org.apache.catalina.connector.Response;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import app.core.exceptions.CouponSystemException;
//import app.core.exceptions.LoginException;
//import app.core.loginManager.LoginManager;
//import app.core.loginManager.Type;
//import app.core.service.ClientService;
//import io.swagger.v3.oas.models.responses.ApiResponse;
//
//@RestController
//@RequestMapping("/api")
//public class CouponSystemController {
//
//	ClientService service;
//	@Autowired
//	private LoginManager loginManager = new LoginManager();
//	
//	@PostMapping(path = "/login")
//	public ClientService login(@RequestParam String email,@RequestParam String password,@RequestParam Type type) throws LoginException{
//		
//		ClientService response = loginManager.login(email, password, type);
//		
//		return response;
//	}
//	
////	public boolean companyExistsById(int id) throws CouponSystemException {
////	}
////	
////	public boolean companyExistsByEmail(String email) throws CouponSystemException {
////	}
//	
//	
//}
