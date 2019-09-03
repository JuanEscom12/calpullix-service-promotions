package com.calpullix.service.promotions.conf;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.calpullix.service.promotions.handler.PromotionsHandler;

@Configuration
public class RoutesConf {

	@Value("${app.path-promotions}")
	private String pathPromotions;
	
	@Value("${app.path-client-promotions}")
	private String pathClientPromotions;
	
	
	@Bean
	public RouterFunction<ServerResponse> routesLogin(PromotionsHandler loginHandler) {
		return route(GET(pathPromotions), loginHandler::getPromotions).and(
				route(POST(pathClientPromotions), loginHandler::getClientPromotions));
	}
	
}
