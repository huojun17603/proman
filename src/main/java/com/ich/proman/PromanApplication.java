package com.ich.proman;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@Configuration
@ImportResource(value = {"classpath:spring/applicationContext-dao.xml",
		"classpath:spring/applicationContext-trans.xml"})
public class PromanApplication {

	public static void main(String[] args) {
		SpringApplication.run(PromanApplication.class, args);
	}

}
