package app.core.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.auth.UserCredentials;
import app.core.entities.User;
import app.core.exceptions.CouponSystemException;
import app.core.exceptions.LoginException;
import app.core.reposetories.CompanyRepo;
import app.core.reposetories.CustomerRepo;
import app.core.service.AuthService;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	


	@PostMapping("/register")
	public String register(@RequestBody User user) {
		try {
			return this.authService.register(user);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());}	
	}

	@PostMapping("/login")
	public String login(@RequestBody UserCredentials userCredentials) {
		try {
			return this.authService.login(userCredentials);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
	
	}

}
}