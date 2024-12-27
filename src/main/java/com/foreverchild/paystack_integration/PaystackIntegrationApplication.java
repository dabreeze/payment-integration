package com.foreverchild.paystack_integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
public class PaystackIntegrationApplication {

	public static void main(String[] args) {
//		SpringApplication.run(PaystackIntegrationApplication.class, args);
		SpringApplication app = new SpringApplication(PaystackIntegrationApplication.class);
		ConfigurableEnvironment environment = app.run(args).getEnvironment();
		environment.setActiveProfiles("local");
	}

}
