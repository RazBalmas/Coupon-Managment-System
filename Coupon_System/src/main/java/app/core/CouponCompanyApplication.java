package app.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication

public class CouponCompanyApplication {

	public static void main(String[] args) {
		SpringApplication.run(CouponCompanyApplication.class, args);
	
	}

}
