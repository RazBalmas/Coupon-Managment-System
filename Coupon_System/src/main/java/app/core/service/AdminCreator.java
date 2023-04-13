package app.core.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.core.entities.Admin;
import app.core.reposetories.AdminRepo;

@Component
public class AdminCreator {
	
	@Autowired
	AdminRepo adminRepo;
	
	@PostConstruct
	public void CreateAdminInDataBase() {
	if (!adminRepo.existsById(1)) {
		Admin admin = Admin.getAdmin();
		adminRepo.save(admin);
	}
	}
}
