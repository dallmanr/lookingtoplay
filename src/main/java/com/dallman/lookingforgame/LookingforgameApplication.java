package com.dallman.lookingforgame;

import com.dallman.lookingforgame.Service.IgdbApiProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(IgdbApiProperties.class)
public class LookingforgameApplication {

	public static void main(String[] args) {
		SpringApplication.run(LookingforgameApplication.class, args);
	}

}
