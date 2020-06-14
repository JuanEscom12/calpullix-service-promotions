package com.calpullix.service.promotions.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PromotionsDetailDTO {

	private Integer id;
	
	private String content;
	
	private String name;
	
	private Integer idProduct;
	
	private String product;
	
	private String description;
	
	private BigDecimal originalPrice;
	
	private BigDecimal discountPrice;
	
	private BigDecimal taxes;
	
	private BigDecimal netIncome;
	
	private String vendor;
	
	private String initDate;
	
	private String endDate;
	
	private String dates;
	
	private BigDecimal amountProfit;
	
	private BigDecimal percentageDiscount;
	
}
