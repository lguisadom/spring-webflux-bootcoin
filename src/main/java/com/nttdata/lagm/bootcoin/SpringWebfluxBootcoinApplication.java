package com.nttdata.lagm.bootcoin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class SpringWebfluxBootcoinApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebfluxBootcoinApplication.class, args);
	}

}
