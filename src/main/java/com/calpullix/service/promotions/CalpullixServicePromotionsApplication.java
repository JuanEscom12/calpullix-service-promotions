package com.calpullix.service.promotions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
@EnableCircuitBreaker
@EnableAutoConfiguration( exclude = RabbitAutoConfiguration.class) 
public class CalpullixServicePromotionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalpullixServicePromotionsApplication.class, args);
	}

}
