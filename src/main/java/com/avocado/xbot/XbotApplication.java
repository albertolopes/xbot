package com.avocado.xbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class XbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(XbotApplication.class, args);
	}

}
