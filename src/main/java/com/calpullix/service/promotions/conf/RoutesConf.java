package com.calpullix.service.promotions.conf;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.calpullix.service.promotions.handler.PromotionsHandler;

@Configuration
public class RoutesConf {

	@Value("${app.path-analysis-products-clients}")
	private String pathAnalysisProductsClients;

	@Value("${app.path-promotions-profile-detail}")
	private String pathPromotionsProfileDetail;

	@Value("${app.path-image-promotions}")
	private String pathImagePromotions;
	
	@Value("${app.path-update-promotions-profile}")
	private String pathUpdatePromotionsProfile;
	
	@Value("${app.path-detail-prmotion}")
	private String pathDetailPromotion;
	
	@Value("${app.path-test-resize}")
	private String pathTestResize;
	
	
	@Bean
	public RouterFunction<ServerResponse> routesLogin(PromotionsHandler loginHandler) {
		return route(POST(pathAnalysisProductsClients), loginHandler::getAnalysisProductsClients)
						.and(route(POST(pathPromotionsProfileDetail), loginHandler::getPromotionsProfile))
							.and(route(POST(pathImagePromotions), loginHandler::getPromotionsImages))
								.and(route(POST(pathUpdatePromotionsProfile), loginHandler::updatePromotionsProfile))
									.and(route(POST(pathDetailPromotion), loginHandler::getDetailPromotion));
	}

}
