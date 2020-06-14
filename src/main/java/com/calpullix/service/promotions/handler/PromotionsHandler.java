package com.calpullix.service.promotions.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.calpullix.service.promotions.model.PromotionsRequestDTO;
import com.calpullix.service.promotions.service.PromotionsService;
import com.calpullix.service.promotions.util.AbstractWrapper;
import com.calpullix.service.promotions.util.ValidationHandler;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class PromotionsHandler {
	
	@Autowired
	private PromotionsService promotionsService;
	
	@Value("${app.message-error-location-body}")
	private String messageErrorLocationBody;

	@Autowired
	private ValidationHandler validationHandler;

	
	@Timed(value = "calpullix.service.analysis.customers.promotions.metrics", description = "Retrieve analysis customers promotions")
	public Mono<ServerResponse> getAnalysisProductsClients(ServerRequest serverRequest) {
		log.info(":: Client promotions retrieve handler {} ", serverRequest);
		return validationHandler.handle(input -> input.flatMap(request -> AbstractWrapper.async(() -> {
			return promotionsService.getAnalysisProductsClients(request);
		})).flatMap(response -> ServerResponse.ok().body(BodyInserters.fromObject(response))), serverRequest,
				PromotionsRequestDTO.class).switchIfEmpty(Mono.error(new Exception(messageErrorLocationBody)));
	}

	@Timed(value = "calpullix.service.promotions.profile.metrics", description = "Retrieve promotions by profile ")
	public Mono<ServerResponse> getPromotionsProfile(ServerRequest serverRequest) {
		log.info(":: Client promotions profile handler {} ", serverRequest);
		return validationHandler.handle(input -> input.flatMap(request -> AbstractWrapper.async(() -> {
			return promotionsService.getPromotionsProfile(request);
		})).flatMap(response -> ServerResponse.ok().body(BodyInserters.fromObject(response))), serverRequest,
				PromotionsRequestDTO.class).switchIfEmpty(Mono.error(new Exception(messageErrorLocationBody)));
	}

	@Timed(value = "calpullix.service.promotions.images.metrics", description = "Retrieve promotions images ")
	public Mono<ServerResponse> getPromotionsImages(ServerRequest serverRequest) {
		log.info(":: Client image promotions handler {} ", serverRequest);
		return validationHandler.handle(input -> input.flatMap(request -> AbstractWrapper.async(() -> {
			return promotionsService.getPromotionsImages(request);
		})).flatMap(response -> ServerResponse.ok().body(BodyInserters.fromObject(response))), serverRequest,
				PromotionsRequestDTO.class).switchIfEmpty(Mono.error(new Exception(messageErrorLocationBody)));
	}

	@Timed(value = "calpullix.service.detail.promotion.metrics", description = "Retreive detail promotion ")
	public Mono<ServerResponse> getDetailPromotion(ServerRequest serverRequest) {
		log.info(":: Retreive detail promotion handler {} ", serverRequest);
		return validationHandler.handle(input -> input.flatMap(request -> AbstractWrapper.async(() -> {
			return promotionsService.getDetailPromotion(request);
		})).flatMap(response -> ServerResponse.ok().body(BodyInserters.fromObject(response))), serverRequest,
				PromotionsRequestDTO.class).switchIfEmpty(Mono.error(new Exception(messageErrorLocationBody)));
	}

	
	@Timed(value = "calpullix.service.update.promotions.profile.metrics", description = "Update promotions profile ")
	public Mono<ServerResponse> updatePromotionsProfile(ServerRequest serverRequest) {
		log.info(":: Update promotions profile handler {} ", serverRequest);
		return validationHandler.handle(input -> input.flatMap(request -> AbstractWrapper.async(() -> {
			return promotionsService.updatePromotionsProfile(request);
		})).flatMap(response -> ServerResponse.ok().body(BodyInserters.fromObject(response))), serverRequest,
				PromotionsRequestDTO.class).switchIfEmpty(Mono.error(new Exception(messageErrorLocationBody)));
	}

}
