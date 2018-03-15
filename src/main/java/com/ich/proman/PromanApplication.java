package com.ich.proman;

import com.ich.config.processor.IConfigProcessor;
import com.ich.international.processor.ILocaleInitProcessor;
import com.ich.module.processor.ModularInitProcessor;
import com.ich.module.processor.ResourcesInitProcessor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@MapperScan({"com.ich.*.dao","com.ich.proman.*.mapper"})
public class PromanApplication {

	public static void main(String[] args) {
		SpringApplication.run(PromanApplication.class, args);
	}

}
