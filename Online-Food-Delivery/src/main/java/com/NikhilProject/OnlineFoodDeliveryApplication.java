package com.NikhilProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "com.NikhilProject")
public class OnlineFoodDeliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineFoodDeliveryApplication.class, args);
	}

}
