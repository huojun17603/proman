package com.ich.proman;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@MapperScan({"com.ich.*.dao","com.ich.proman.*.mapper"})
public class PromanApplication {

	public static void main(String[] args) {
		SpringApplication.run(PromanApplication.class, args);
	}

}
