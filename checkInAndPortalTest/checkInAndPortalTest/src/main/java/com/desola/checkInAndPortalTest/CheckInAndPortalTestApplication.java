package com.desola.checkInAndPortalTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan({"com.desola.checkInAndPortalTest"})
@SpringBootApplication
public class CheckInAndPortalTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckInAndPortalTestApplication.class, args);
	}

}
