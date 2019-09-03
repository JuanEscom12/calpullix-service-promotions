package com.calpullix.service.promotions.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.calpullix.service.promotions.model.ClientPromotionsResponse;
import com.calpullix.service.promotions.model.CustomerDetail;
import com.calpullix.service.promotions.model.PromotionsDetail;
import com.calpullix.service.promotions.model.PromotionsRequest;
import com.calpullix.service.promotions.model.PromotionsResponse;
import com.calpullix.service.promotions.util.AbstractWrapper;
import com.calpullix.service.promotions.util.ValidationHandler;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class PromotionsHandler {

	@Value("${app.message-error-location-body}")
	private String messageErrorLocationBody;

	@Autowired
	private ValidationHandler validationHandler;

	@Timed(value = "calpullix.service.promotions.metrics", description = "Retrieve promotions operation")
	public Mono<ServerResponse> getPromotions(ServerRequest serverRequest) {
		log.info(":: Retrieve promotions handler {} ", serverRequest);
		return AbstractWrapper.async(() -> getPromotionsResponse())
						.flatMap(response -> ServerResponse.ok().body(BodyInserters.fromObject(response)));
	}
	
	private PromotionsResponse getPromotionsResponse() {
		final PromotionsResponse result = new PromotionsResponse();
		final List<PromotionsDetail> detail = new ArrayList<>();
		PromotionsDetail item;
		for(int index = 0; index < 5; index++) {
			item = new PromotionsDetail();
			item.setContent("Nombre de la promocion:\n" +
							"Promocion " + index + "\n" + 
							"Id del producto:\n" +
							"90798564 \n" +
							"Descripcion: \n" + 
							"Descripcion de la promocion. \n" +
							"Precio original: \n" +
							"$ 128.90 \n" + 
							"Precio de la promocion: \n" +
							"$ 120.90 \n" +
							"Pprcentaje de descuento: \n" +
							"20% \n" +
							"Monto de la ganancia: " +
							"$ 14.00 \n");
			detail.add(item);
		}
		result.setPromotions(detail);
		return result;
	}
	
	@Timed(value = "calpullix.service.client.promotions.metrics", description = "Retrieve client promotions operation")
	public Mono<ServerResponse> getClientPromotions(ServerRequest serverRequest) {
		log.info(":: Client promotions retrieve handler {} ", serverRequest);
		return validationHandler.handle(input -> input.flatMap(request -> AbstractWrapper.async(() -> 
			getClientPromotionsResponse()
		)).flatMap(response -> ServerResponse.ok().body(BodyInserters.fromObject(response))), serverRequest,
				PromotionsRequest.class).switchIfEmpty(Mono.error(new Exception(messageErrorLocationBody)));

	}
	
	private ClientPromotionsResponse getClientPromotionsResponse() {
		final ClientPromotionsResponse result = new ClientPromotionsResponse();
		result.setIdProducto(79787);
		result.setProductName("Nombre del producto XXX");
		result.setUrlProductImage("https://images.app.goo.gl/8j94xzup6fefRLcG8");
		final List<CustomerDetail> customers = new ArrayList<CustomerDetail>();
		CustomerDetail item;
		for (int index = 0; index < 8; index++) {
			item = new CustomerDetail();
			item.setContact("557900908");
			item.setIdUser(22828209);
			item.setName("Pablo Rodriguez Meza");
			customers.add(item);
		}
		result.setCustomers(customers);
		return result;
	}

}
