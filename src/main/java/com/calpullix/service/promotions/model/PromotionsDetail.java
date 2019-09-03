package com.calpullix.service.promotions.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionsDetail {

	private String id;
	
	private String content;
	
	private String name;
	
	private Integer idProducto;
	
	private String description;
	
	private BigDecimal originalPrice;
	
	private BigDecimal discountPrice;
	
	private Integer percentageDiscount;
	
	private BigDecimal amountProfit;
	
}
