package app.core.job;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.websocket.OnOpen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import app.core.entities.Coupon;
import app.core.service.CouponService;

@Component
public class Job implements CommandLineRunner {

	@Autowired
	CouponService service;
	
	public Job() {
		
	}

	@Scheduled(cron = "0 0 * * *")
	@Override
	public void run(String... args) throws Exception {
		
		List<Coupon> allCoupons = service.getAllCoupons();
		System.out.println("Job started");
		for(Coupon coupon : allCoupons) {
			if(coupon.getEndDate().isBefore(LocalDate.now())) {
				service.deleteCoupon(coupon.getId());
			
				System.out.println("===================================");
				System.out.println("Job deleted coupon : " +coupon.getId());
				System.out.println("===================================");
		}
		}
		

	}

}
