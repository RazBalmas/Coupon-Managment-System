package app.core.DataGenaration;

import java.time.LocalDate;

import app.core.entities.Catagory;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.service.CompanyService;


public class DataGenarator {

	public DataGenarator() {
		// TODO Auto-generated constructor stub
	}

	public enum LastNames {
		COHEN, LEVI, MERDOK, SWALY, WRETCH, SHLOMO, STEWERT, KENNEDY, LAZZAR, PO, SHWARSKY, LENNON;
	}

	public enum firstNames {
		RUTHI, RAZ, DANA, AHRON, BOB, QUEENCY, MARK, JHON, AHARON, KELLY, ROSS, RACHEL, HANNA;
	}

	public enum companyNames {
		ASUS, BANANA, YY_BROTHERS, DAN, STARKIST, BEAN_BAGS, RAMI_LEVI, MEGAGLUEFLEX, OSEM, KINDER, POLGAT, MAZDA;
	}
	

	public companyNames getCompanyName() {
		companyNames [] random = companyNames.values();
		return random[(int)(Math.random()*random.length)];
	}

	public firstNames getFirstName() {
		firstNames [] random = firstNames.values();
		return random[(int)(Math.random()*random.length)];
	}

	public LastNames getLastName() {
		LastNames [] random = LastNames.values();
		return random[(int)(Math.random()*random.length)];
	}

	public Customer genarteRandomCustomer() {

		int random = (int) (Math.random() * 1000);
		Customer customer = new Customer();
		String firstName = getFirstName().toString();
		String lastName = getLastName().toString();
		customer.setId(0);
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setEmail(firstName + lastName + "@mail.com");
		customer.setPassword(firstName + random + lastName);
		customer.setOwned_coupons(null);
		return customer;

	}

	public Company genarteRandomCompany() {
		int random = (int) (Math.random() * 1000);
		Company company = new Company();
		String companyName = getCompanyName().toString();

		company.setId(0);
		company.setName(companyName);
		company.setEmail(companyName + "@mail.com");
		company.setPassword(companyName + random);
		company.setCoupons(null);
		return company;
	}

	public Coupon genarteRandomCoupon(Company company) {

		String companyName;

			companyName = company.getName();

			int random_amount = (int) (Math.random() * 100 + 1);
			Coupon coupon = new Coupon();
			LocalDate startDate = LocalDate.now().plusDays((long) (Math.random() * 3)).plusDays(1);
			LocalDate endDate = LocalDate.now().plusDays((long) (Math.random() * 33 + 3));
			Catagory [] catagorys = Catagory.values();
			Catagory catagory = catagorys[(int)Math.random()*catagorys.length];
			double random_price = Math.random() * 950 + 50;

			coupon.setId(0);
			coupon.setAmount(random_amount);
			coupon.setCatagory(catagory);
			coupon.setCompany(company);
			coupon.setDescription(catagory.toString() + " Coupon of company " + companyName + "(" + company.getId() + ")");
			coupon.setStartDate(startDate);
			coupon.setEndDate(endDate);
			coupon.setImage("Image of coupon");
			coupon.setPrice(random_price);
			coupon.setTitle(catagory.toString() + " coupon of company " + companyName);
			return coupon;

	}
}
