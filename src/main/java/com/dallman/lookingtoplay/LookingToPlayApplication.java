package com.dallman.lookingtoplay;

import com.dallman.lookingtoplay.Service.IgdbApiProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(IgdbApiProperties.class)
public class LookingToPlayApplication {

	public static void main(String[] args) {
		SpringApplication.run(LookingToPlayApplication.class, args);
	}

}
