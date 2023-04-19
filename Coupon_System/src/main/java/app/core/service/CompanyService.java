package app.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.exceptions.CouponSystemException;
import app.core.reposetories.CompanyRepo;
import app.core.reposetories.CouponRepo;

@Service
@Scope("prototype")
@Transactional
public class CompanyService extends ClientService{

	@Autowired
	private CompanyRepo company_repo;
	@Autowired
	private CouponRepo coupon_repo;

	/////////////////////////////////////////////////////////////////////////////////

//										Company

	/////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public boolean login(String email, String password) {
		try {
			if (findByCompanyEmail(email).getPassword().equals(password)) {
				return true;
			}
		} catch (CouponSystemException e) {
			System.out.println("Wrong email or Password");
		}
		return false;
	}
	public boolean companyExistsById(int id) throws CouponSystemException {
		if (company_repo.existsById(id)) {
			return true;
		}		
		return false;
	}

	public boolean companyExistsByEmail(String email) throws CouponSystemException {
		if (this.company_repo.existsByEmail(email)) {
			return true;
		}
		return false;
	}

	public void updateCompany(Company company) throws CouponSystemException {
		if (companyExistsById(company.getId())) {
			company_repo.save(company);
		} else
			throw new CouponSystemException("Company was not found in DataBase to Update.");
	}

	public void deleteCompany(int id) throws CouponSystemException {
		if (companyExistsById(id)) {
			company_repo.deleteById(id);
		} else
			throw new CouponSystemException("Company was not found in DataBase.");
	}

	public List<Company> getAllCompanies() {

		return company_repo.findAll();
	}

	public Company findByCompanyId(int id) throws CouponSystemException {
		if (companyExistsById(id)) {
			try {
				Optional<Company> opt = company_repo.findById(id);
				if (opt.isPresent()) {
					Company company = opt.get();
					return company;
				}
			} catch (Exception e) {
				throw new CouponSystemException("Failed to retrive company from DataBase");
			}
		}
		throw new CouponSystemException("Company not found in DataBase by specified ID.");
	}

	public Company findByCompanyEmail(String email) throws CouponSystemException {
		if (companyExistsByEmail(email)) {
			try {
				Company company = company_repo.findByEmail(email);
				return company;
			} catch (CouponSystemException e) {

				throw new CouponSystemException("Failed to retrive Company from DataBase");
			}
		}
		throw new CouponSystemException("Company not found in DataBase by specified ID.");
	}

	public int addCoupon(Coupon coupon) throws CouponSystemException {
		if (!coupon_repo.existsById(coupon.getId())) {
			Coupon updated_coupon =coupon_repo.save(coupon);
			return updated_coupon.getId();
		} else
			throw new CouponSystemException("Coupon cannot be added, already exists in DataBase.");
	}

	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		if (coupon_repo.existsById(coupon.getId())) {
			coupon_repo.save(coupon);
		} else
			throw new CouponSystemException("Coupon cannot be updated, cannot be found in DataBase.");

	}

	public void deleteCoupon(int coupon_id) throws CouponSystemException {
		if (coupon_repo.existsById(coupon_id)) {
			coupon_repo.deleteById(coupon_id);
		} else
			throw new CouponSystemException("Coupon cannot be found in DataBase.");

	}

	public List<Coupon> findCouponsByCompany_Id(int company_id) throws CouponSystemException {

		List<Coupon> all_coupons = coupon_repo.findCouponByCompany_Id(company_id);
//		List<Coupon> company_coupons = new ArrayList<>();
//		for (Coupon coupon : all_coupons) {
//			if (coupon.getCompany().getId() == company_id) {
//				company_coupons.add(coupon);
//			}
//		}
		return all_coupons;
	}
	

}
