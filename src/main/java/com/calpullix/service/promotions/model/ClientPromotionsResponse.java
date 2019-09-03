package com.calpullix.service.promotions.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientPromotionsResponse {

	private Integer idProducto;
	
	private String productName;
	
	private String urlProductImage;

	private List<CustomerDetail> customers;
	
}
