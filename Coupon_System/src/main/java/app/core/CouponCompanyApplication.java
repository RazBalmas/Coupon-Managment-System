package app.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan
public class CouponCompanyApplication {

	public static void main(String[] args) {
		SpringApplication.run(CouponCompanyApplication.class, args);
	
	}

}
