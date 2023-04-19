//package app.core;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import app.core.entities.Company;
//import app.core.reposetories.CompanyRepo;
//
//@Component
//public class TestEldar implements CommandLineRunner {
//	
//	@Autowired
//	CompanyRepo companyRepo;
//
//	@Override
//	public void run(String... args) throws Exception {
//		Company company = new Company(0, "xyz", "aaa@mail", "12345");
//		companyRepo.save(company);
//		System.out.println("saved");
//		
//	}
//
//}
