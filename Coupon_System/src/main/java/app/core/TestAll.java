package app.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import app.core.DataGenaration.DataGenarator;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.job.Job;
import app.core.loginManager.LoginManager;
import app.core.loginManager.ClientType;
import app.core.service.AdminService;
import app.core.service.ClientService;
import app.core.service.CompanyService;
import app.core.service.CustomerService;

//Automatic testing when system loads.

//@Component
public class TestAll implements CommandLineRunner {

	private DataGenarator genarator = new DataGenarator();
	
	@Autowired
	Job job;
	@Autowired
	LoginManager manager;
	
	public TestAll() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(String... args) throws Exception {
			
			try {
				////////////////////////////////////////////////////////////////////////////////////////
				////////////////////////////////////////////////////////////////////////////////////////
				////////////////////////////////////////////////////////////////////////////////////////
			
				System.out.println("======================================================");
				System.out.println("======================================================");
				System.out.println("=======================System TEST=====================");
				System.out.println("======================================================");
				System.out.println("======================================================");
				
				System.out.println("======================================================");
				System.out.println("======================================================");
				System.out.println("=======================ADMIN TEST=====================");
				
				ClientService service = manager.login("admin@admin.com", "admin", ClientType.ADMIN);
				AdminService admin = (AdminService) service;
				Company company = genarator.genarteRandomCompany();
				System.out.println("=====================================================");
				System.out.println("=====================================================");
				System.out.println("===================Adding a Company==================");
				int companyID = admin.addCompany(company);
				company.setId(companyID);
				System.out.println("Company : \n" + company.toString());
				System.out.println("\n=====================================================");
				System.out.println("=====================================================");
				System.out.println("\n Company exists by email : " + company.getEmail());
				System.out.println(admin.companyExistsByEmail(company.getEmail()));
				System.out.println("\n=====================================================");
				System.out.println("=====================================================");
				System.out.println("\n Company exists by ID : " + company.getId());
				System.out.println(admin.companyExistsById(companyID));
				System.out.println("\n=====================================================");
				System.out.println("=====================================================");
				System.out.println("\n Company update Coupons : " + company.getName());
				
				Coupon coupon1 = genarator.genarteRandomCoupon(company);
				Coupon coupon2 = genarator.genarteRandomCoupon(company);
				Coupon coupon3 = genarator.genarteRandomCoupon(company);
				List<Coupon> companyCoupons = new ArrayList<>();
				companyCoupons.add(coupon1);
				companyCoupons.add(coupon2);
				companyCoupons.add(coupon3);
				company.setCoupons(companyCoupons);
				admin.updateCompany(company);
				System.out.println("Company Coupons updated");
				
				System.out.println("\n=====================================================");
				System.out.println("=====================================================");
				System.out.println("\n Find Coupons by Company ID : " + companyID);
				companyCoupons = admin.findCouponsByCompany_Id(companyID);
				System.out.println("\n" +companyCoupons.toString());
				
				System.out.println("\n=====================================================");
				System.out.println("=====================================================");
				System.out.println("\n Addin a customer : " + companyID);
				Customer customer = genarator.genarteRandomCustomer();
				int customerID = admin.addCustomer(customer);
				customer.setId(customerID);
				System.out.println(customer.toString());
				
				System.out.println("\n=====================================================");
				System.out.println("=====================================================");
				System.out.println("\n Making a customer purches :Cusomer " + customer.getFirstName() + " " + customer.getLastName() +
						" , Coupon: " + coupon1.getTitle());
				Coupon coupon1_with_ID = admin.findCouponByTitle(coupon1.getTitle());
				admin.addCouponPurchase(customerID, coupon1_with_ID.getId());
				
				System.out.println("\n=====================================================");
				System.out.println("=====================================================");
				System.out.println("\n All Coupons :" );
				List<Coupon> all_Coupons = admin.getAllCoupons();
				System.out.println("Coupons : \n");
				for (Coupon c : all_Coupons) {
					System.out.println("Coupon : \n" + c.toString());
				}
				System.out.println("\n=====================================================");
				System.out.println("=====================================================");
				System.out.println("Admin Test Was Compleated Sucssefully!");
				System.out.println("\n=====================================================");
				System.out.println("=====================================================");
				
				////////////////////////////////////////////////////////////////////////////////////////
				////////////////////////////////////////////////////////////////////////////////////////
				////////////////////////////////////////////////////////////////////////////////////////
				System.out.println("\n====================Company test=====================");
				System.out.println("=====================================================");
				System.out.println("========================Login========================");
				service = manager.login(company.getEmail(), company.getPassword(), ClientType.COMPANY);
				CompanyService companyServ = (CompanyService) service;
				System.out.println("Logged in sucsessfully!");
				System.out.println("================Finding Company by ID================");
				System.out.println("Company with id : " + company.getId() + " exists in DataBase : " + companyServ.companyExistsById(company.getId()));
				System.out.println("=====================================================");
				System.out.println("=====================================================");
				System.out.println("================Getting a Company by ID==============");
				Company companyById = companyServ.findByCompanyId(company.getId());
				System.out.println("ID : " + companyById.getId() +" \n" + companyById.toString());
				System.out.println("=====================================================");
				System.out.println("=====================================================");
				System.out.println("===============Adding Company Coupon================");
				Coupon coupon = genarator.genarteRandomCoupon(company);
				int CouponID = companyServ.addCoupon(coupon);
				coupon.setId(CouponID);
				System.out.println("Coupon was added to the company : \n" + coupon.toString());
				System.out.println("=====================================================");
				System.out.println("=====================================================");
				System.out.println("============Deleting Company Coupon by ID============");
				companyServ.deleteCoupon(CouponID);
				System.out.println("Coupon with Id: " + CouponID +" was removed");
				System.out.println("=====================================================");
				System.out.println("=====================================================");
				System.out.println("===============Getting All Companies================");
				System.out.println("All Companies in DataBase: \n" + companyServ.getAllCompanies().toString());
				
				System.out.println("\n=====================================================");
				System.out.println("=====================================================");
				System.out.println("Company Test Was Compleated Sucssefully!");
				System.out.println("\n=====================================================");
				System.out.println("=====================================================");
				
				////////////////////////////////////////////////////////////////////////////////////////
				////////////////////////////////////////////////////////////////////////////////////////
				////////////////////////////////////////////////////////////////////////////////////////
				System.out.println("\n====================Customer test=====================");
				System.out.println("=====================================================");
				System.out.println("========================Login========================");
				service = manager.login(customer.getEmail(), customer.getPassword(), ClientType.CUSTOMER);
				CustomerService customerServ = (CustomerService) service;
				System.out.println("Customer logged in sucsessfully!");
				System.out.println("=====================================================");
				System.out.println("=====================================================");
				System.out.println("===================Adding a Company==================");
				customer = genarator.genarteRandomCustomer();
				customerID = customerServ.addCustomer(customer);
				customer.setId(customerID);
				System.out.println("Customer was added : " + customer.toString());
				System.out.println("=====================================================");
				System.out.println("=====================================================");
				System.out.println("===============Is Customer Exists By ID==============");
				System.out.println(customerServ.CustomerExistsById(customerID));
				System.out.println("=====================================================");
				System.out.println("=====================================================");
				System.out.println("===============Finding a Customer By ID==============");
				Customer cust = customerServ.findByCustomerId(customerID);
				System.out.println("Customer found in DataBase: \n" + cust.toString());
				System.out.println("=====================================================");
				System.out.println("=====================================================");
				System.out.println("===============Adding a Coupon Purches===============");
				customerServ.addCouponPurchase(customerID, all_Coupons.get(all_Coupons.size()-1).getId());
				System.out.println("=====================================================");
				System.out.println("=====================================================");
				System.out.println("===============Getting all Customers=================");
				List<Customer> all_customers = customerServ.getAllCustomers();
				System.out.println("Customers : \n");
				for(Customer c : all_customers) {
					System.out.println("Customer : " + c.getId() + "\n " + c.toString());
				}
				System.out.println("=====================================================");
				System.out.println("=====================================================");
				System.out.println("===============Adding Coupon Purches=================");
				customerServ.addCouponPurchase(customerID, 1);
				System.out.println("Coupon : "+ coupon.getId() +"Was bought by Customer : " + customerID);
				
				System.out.println("Test Compleated");
				
				
			} catch (CouponSystemException e) {
				
				new CouponSystemException("Test Failed : " , e);
			}

		}

	}


